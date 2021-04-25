package com.example.scanningreceiptstest.database

import android.app.Person
import android.util.Log
import com.example.scanningreceiptstest.Controller.InvitePartner
import com.example.scanningreceiptstest.Model.ExpenseGroup
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlin.math.exp

//variables to hold the current user and expense group
var CURRENT_USER: Person? = null
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

        userRef.child(phoneNum).addValueEventListener( object : ValueEventListener {
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

        expenseGroupRef.child(groupId).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val group = snapshot.getValue(DBExpenseGroup::class.java)
                if(group != null)
                    onDataRetrieved(group)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    /****Sign up****/
//    fun checkIfUserExist(phoneNum: String): Boolean {
//        //check if this phone num already exists in the database
//        ////search for: ref.users.phoneNum
//    }

    fun addNewUser(user: DBPerson) {
        //add this user to the DB (the id is the phone num)
        ////create: ref.users.phoneNum.push() like sendInvitation()
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
    }

    /****Add income****/
    fun addNewIncome(phoneNum: String, income: DBIncome) {
        // and add the income object to the income node
        ////add to the list in: ref.income.phoneNum.push()
    }

    /****Invite partner****/
    fun sendInvitation(invitation: DBInvitation) {
        //add the invitation to the user's invitations
        ////add to the list in: ref.invitation.phoneNum.push()

        invitationRef.child(invitation.receiverPhoneNum).push().setValue(invitation)
    }

    /****Transaction history and Report and Home?****/
    fun getAllExpenses(phoneNum: String, onDataRetrievedFun: (list: List<DBExpense>) -> Unit) {
        //get a list of all user's expenses
        ////get the list: ref.expense.phoneNum

        expenseRef.child(phoneNum).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val expenseList = mutableListOf<DBExpense>()

                for (data in snapshot.children){
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

        incomeRef.child(phoneNum).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val incomeList = mutableListOf<DBIncome>()
                for (data in snapshot.children){
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
    fun getAllInvitations(phoneNum: String, onDataRetrievedFun: (list: List<DBInvitation>) -> Unit) {
        //get a list of all user's invitations
        ////get the list: ref.invitation.phoneNum

        //get all invitations sent to this phoneNum:
        invitationRef.child(phoneNum).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                //a list to store the invitations:
                val invitations = mutableListOf<DBInvitation>()

                //convert each invitation to DBInvitation object then add it to the list
                for (data in snapshot.children) {
                    val invite = data.getValue(DBInvitation::class.java)
                    if(invite != null)
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
    }
}