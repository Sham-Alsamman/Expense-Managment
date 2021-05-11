package com.example.scanningreceiptstest.database

import android.widget.Toast
import at.favre.lib.crypto.bcrypt.BCrypt
import com.example.scanningreceiptstest.Model.ExpenseGroup
import com.example.scanningreceiptstest.Model.Person
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

//variables to hold the current user and expense group
var CURRENT_USER: Person? = /*Person("Sham", "+123456789", "")*/null
var CURRENT_GROUP: ExpenseGroup? = null

object Database {

    private val database = Firebase.database
    private val expenseGroupRef = database.getReference("expense_group")
    private val invitationRef = database.getReference("invitation")
    private val userRef = database.getReference("user")
    private val expenseRef = database.getReference("expense")
    private val incomeRef = database.getReference("income")

    /****Login*****/
    //this parameter is the function to call when the data changes
    fun getUser(phoneNum: String, onDataRetrieved: (DBPerson) -> Unit) {
        //get the person obj with this phone num
        //where to store password????
        ////get: ref.users.phoneNum

        userRef.child(phoneNum).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue(DBPerson::class.java)
                if (user != null)
                    onDataRetrieved(user)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    fun getExpenseGroup(groupId: String, onDataRetrieved: (DBExpenseGroup) -> Unit) {
        //get the (current) expense group using the groupId in the Person obj
        ////get: ref.expense_group.groupId

        expenseGroupRef.child(groupId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val group = snapshot.getValue(DBExpenseGroup::class.java)
                if (group != null)
                    onDataRetrieved(group)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    fun CheckPassword(Phone :String ,Pass:String ,onDataRetrieved: (Boolean) -> Unit) {
        var flag=true
        var p: DBPerson?=null
        var passval = Pass.toString().trim()
        userRef.orderByChild("phoneNumber").equalTo(Phone).addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (postSnapshot in snapshot.children) {
                        p= postSnapshot.getValue(DBPerson::class.java)
                    }
                    val l = p?.password.toString()
                    val result = BCrypt.verifyer().verify(passval.toCharArray(), l)
                    if (result.verified) {
                        // correct password
                       flag=true
                    } else {
                        //not correct password
                       flag=false
                    }
                }
                else{
                   // phone number not exist
                    flag=false
                }
                onDataRetrieved(flag)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }



    /****Sign up****/

    fun checkIfUserExist(phoneNum: String, onDataRetrieved: (Boolean) -> Unit) {
        //check if this phone num already exists in the database
        ////search for: ref.users.phoneNum


        userRef.child(phoneNum).addListenerForSingleValueEvent( object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var exist = false
                if(snapshot.exists())
                    exist = true

                onDataRetrieved(exist)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }


    fun addNewUser(user: DBPerson, onAddedSuccessfully: (Boolean) -> Unit) {
        //add this user to the DB (the id is the phone num)
        ////create: ref.users.phoneNum

        userRef.child(user.phoneNumber).setValue(user).addOnCompleteListener { task ->
            if (task.isSuccessful)
                onAddedSuccessfully(true)
            else
                onAddedSuccessfully(false)
        }
    }

    //this method is called when a new user sign up, to put him in a new group
    fun addNewExpenseGroup(group: DBExpenseGroup): DBExpenseGroup {
        ////create: ref.expense_group.groupId
        //get auto-generated id
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
    fun addNewExpense(phoneNum: String, expense: DBExpense) {
        // and add the expense object to the expense node
        ////add to the list in: ref.expense.phoneNum.push() like sendInvitation()

        expenseRef.child(phoneNum).push().setValue(expense)
    }

    /****Add income****/
    fun addNewIncome(phoneNum: String, income: DBIncome) {
        // and add the income object to the income node
        ////add to the list in: ref.income.phoneNum.push()

        incomeRef.child(phoneNum).push().setValue(income)
    }

    /****Invite partner****/
    fun sendInvitation(invitation: DBInvitation) {
        //add the invitation to the user's invitations
        ////add to the list in: ref.invitation.phoneNum.push()
        val id = invitationRef.push().key
        if (id != null) {
            invitation.id = id
            invitationRef.child(invitation.receiverPhoneNum).child(id).setValue(invitation)
        }
    }

    /****Transaction history and Report and Home?****/
    fun getAllExpenses(phoneNum: String, onDataRetrievedFun: (list: List<DBExpense>) -> Unit) {
        //get a list of all user's expenses
        ////get the list: ref.expense.phoneNum

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

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    fun getAllIncomes(phoneNum: String, onDataRetrievedFun: (list: List<DBIncome>) -> Unit) {
        //get a list of all user's incomes
        ////get the list: ref.income.phoneNum

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

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    /****Invitations****/
    //this parameter is the function to call when the data changes
    fun getAllInvitations(
        phoneNum: String,
        onDataRetrievedFun: (list: List<DBInvitation>) -> Unit
    ) {
        //get a list of all user's invitations
        ////get the list: ref.invitation.phoneNum

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

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    /****Profile****/
    /***called when any field in Person obj changes
     * (in Wallet screen and when adding new expense or income, etc..)****/
    fun updateUserInfo(phoneNum: String, user: DBPerson) {
        //update this user's data
        ////update: ref.users.phoneNum
        val hashMap = HashMap<String, Any>()
        hashMap.put("groupId",user.groupId)
        hashMap.put("monthlySalary",user.monthlySalary)
        hashMap.put("name",user.name)
        hashMap.put("phoneNumber",user.phoneNumber)
        hashMap.put("savingAmount",user.savingAmount)
        hashMap.put("savingWallet",user.savingWallet)
        hashMap.put("totalIncome",user.totalIncome)

        userRef.child(phoneNum).updateChildren(hashMap)
    }

    fun updateInvitation(phoneNum: String, invitation: DBInvitation) {
        val hashMap = HashMap<String, Any>()
        hashMap["id"] = invitation.id
        hashMap["senderName"] = invitation.senderName
        hashMap["receiverPhoneNum"] = invitation.receiverPhoneNum
        hashMap["groupID"] = invitation.groupID
        hashMap["invitationStatus"] = invitation.invitationStatus

        invitationRef.child(phoneNum).child(invitation.id).updateChildren(hashMap)
    }

    fun updateExpenseGroup(groug: DBExpenseGroup) {
        val hashMap = HashMap<String, Any>()
        hashMap["groupID"] = groug.groupID
        hashMap["partners"] = groug.partners

        expenseGroupRef.child(groug.groupID).updateChildren(hashMap)
    }
}