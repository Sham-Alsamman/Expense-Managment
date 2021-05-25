package com.example.scanningreceiptstest.database

import android.util.Log
import at.favre.lib.crypto.bcrypt.BCrypt
import com.example.scanningreceiptstest.Model.ExpenseGroup
import com.example.scanningreceiptstest.Model.Person
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

//variables to hold the current user and expense group
var CURRENT_USER: Person? = null
var CURRENT_GROUP: ExpenseGroup? = null

object FirebaseDatabase : IDatabase {

    private val database = Firebase.database
    private val expenseGroupRef = database.getReference("expense_group")
    private val invitationRef = database.getReference("invitation")
    private val userRef = database.getReference("user")
    private val expenseRef = database.getReference("expense")
    private val incomeRef = database.getReference("income")

    /****Login*****/
    //this parameter is the function to call when the data changes
    override fun getUser(phoneNum: String, onDataRetrieved: (DBPerson) -> Unit) {

        userRef.child(phoneNum).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue(DBPerson::class.java)
                if (user != null)
                    onDataRetrieved(user)
            }

            override fun onCancelled(error: DatabaseError) {  }
        })
    }

    override fun getExpenseGroup(groupId: String, onDataRetrieved: (DBExpenseGroup) -> Unit) {

        expenseGroupRef.child(groupId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val group = snapshot.getValue(DBExpenseGroup::class.java)
                if (group != null)
                    onDataRetrieved(group)
            }

            override fun onCancelled(error: DatabaseError) {  }
        })
    }

    override fun checkPassword(Phone: String, Pass: String, onDataRetrieved: (Boolean) -> Unit) {
        var flag: Boolean
        var p: DBPerson? = null
        val passval = Pass.trim()
        userRef.orderByChild("phoneNumber").equalTo(Phone).addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (postSnapshot in snapshot.children) {
                        p = postSnapshot.getValue(DBPerson::class.java)
                    }
                    val l = p?.password.toString()
                    val result = BCrypt.verifyer().verify(passval.toCharArray(), l)
                    flag = result.verified
                } else {
                    // phone number not exist
                    flag = false
                }
                onDataRetrieved(flag)
            }

            override fun onCancelled(error: DatabaseError) {  }
        })
    }

    /****Sign up****/
    override fun checkIfUserExist(phoneNum: String, onDataRetrieved: (Boolean) -> Unit) {

        userRef.child(phoneNum).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var exist = false
                if (snapshot.exists())
                    exist = true

                onDataRetrieved(exist)
            }

            override fun onCancelled(error: DatabaseError) {  }
        })
    }


    override fun addNewUser(user: DBPerson, onAddedSuccessfully: (Boolean) -> Unit) {

        userRef.child(user.phoneNumber).setValue(user).addOnCompleteListener { task ->
            if (task.isSuccessful)
                onAddedSuccessfully(true)
            else
                onAddedSuccessfully(false)
        }
    }

    //this method is called when a new user sign up, to put him in a new group
    override fun addNewExpenseGroup(group: DBExpenseGroup): DBExpenseGroup {

        val id = expenseGroupRef.push().key

        if (id != null) {
            //store the id in the group
            group.groupID = id
            //store the group in the DB
            expenseGroupRef.child(id).setValue(group)
        }
        //return the group to the calling class to let it know the new group id
        return group
    }

    /****Add expense****/
    override fun addNewExpense(phoneNum: String, expense: DBExpense) {

        expenseRef.child(phoneNum).push().setValue(expense)
    }

    /****Add income****/
    override fun addNewIncome(phoneNum: String, income: DBIncome) {

        incomeRef.child(phoneNum).push().setValue(income)
    }

    /****Invite partner****/
    override fun sendInvitation(invitation: DBInvitation) {

        val id = invitationRef.push().key
        if (id != null) {
            invitation.id = id
            invitationRef.child(invitation.receiverPhoneNum).child(id).setValue(invitation)
        }
    }

    /****Transaction history and Report and Home?****/
    override fun getAllExpenses(phoneNum: String, onDataRetrievedFun: (list: List<DBExpense>) -> Unit) {

        expenseRef.child(phoneNum).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val expenseList = mutableListOf<DBExpense>()

                for (data in snapshot.children) {
                    val expense = data.getValue(DBExpense::class.java)
                    if (expense != null)
                        expenseList.add(expense)
                }

                onDataRetrievedFun(expenseList)
            }

            override fun onCancelled(error: DatabaseError) {  }
        })
    }

    override fun getAllIncomes(phoneNum: String, onDataRetrievedFun: (list: List<DBIncome>) -> Unit) {

        incomeRef.child(phoneNum).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val incomeList = mutableListOf<DBIncome>()
                for (data in snapshot.children) {
                    val income = data.getValue(DBIncome::class.java)
                    if (income != null)
                        incomeList.add(income)
                }

                onDataRetrievedFun(incomeList)
            }

            override fun onCancelled(error: DatabaseError) { }
        })
    }

    /****Invitations****/
    //this parameter is the function to call when the data changes
    override fun getAllInvitations(
        phoneNum: String,
        onDataRetrievedFun: (list: List<DBInvitation>) -> Unit
    ) {

        //get all invitations sent to this phoneNum:
        invitationRef.child(phoneNum).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                //a list to store the invitations:
                val invitations = mutableListOf<DBInvitation>()

                //convert each invitation to DBInvitation object then add it to the list
                for (data in snapshot.children) {
                    val invite = data.getValue(DBInvitation::class.java)
                    if (invite != null)
                        invitations.add(invite)
                }
                //call the function (that is given as a parameter) to pass the data back to the calling class
                onDataRetrievedFun(invitations as List<DBInvitation>)
            }

            override fun onCancelled(error: DatabaseError) {  }
        })
    }

    /****Profile****/
    //called when any field in Person obj changes
    override fun updateUserInfo(user: DBPerson) {

        val hashMap = HashMap<String, Any>()
        hashMap.put("groupId", user.groupId)
        hashMap.put("monthlySalary", user.monthlySalary)
        hashMap.put("name", user.name)
        hashMap.put("phoneNumber", user.phoneNumber)
        hashMap.put("savingAmount", user.savingAmount)
        hashMap.put("savingWallet", user.savingWallet)
        hashMap.put("totalIncome", user.totalIncome)
        hashMap.put("password", user.password)
        hashMap.put("remaining", user.remaining)

        userRef.child(user.phoneNumber).updateChildren(hashMap)
    }

    override fun updateInvitation(phoneNum: String, invitation: DBInvitation) {
        val hashMap = HashMap<String, Any>()
        hashMap["id"] = invitation.id
        hashMap["senderName"] = invitation.senderName
        hashMap["receiverPhoneNum"] = invitation.receiverPhoneNum
        hashMap["groupID"] = invitation.groupID
        hashMap["invitationStatus"] = invitation.invitationStatus

        invitationRef.child(phoneNum).child(invitation.id).updateChildren(hashMap)
    }

    override fun updateExpenseGroup(group: DBExpenseGroup) {
        val hashMap = HashMap<String, Any>()
        hashMap["groupID"] = group.groupID
        hashMap["partners"] = group.partners

        expenseGroupRef.child(group.groupID).updateChildren(hashMap)
    }

    override fun deleteExpenseGroup(groupId: String) {
        expenseGroupRef.child(groupId).removeValue()
    }
}