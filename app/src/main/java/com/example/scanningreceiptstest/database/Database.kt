package com.example.scanningreceiptstest.database

import com.example.scanningreceiptstest.database.Database.database
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

object Database {

    val database = Firebase.database

    /****Login*****/
//    fun getUser(phoneNum: String): DBPerson {
//        //get the person obj with this phone num
//        //where to store password????
//        ////get: ref.users.phoneNum
//    }

//    fun getExpenseGroup(groupId: String): DBExpenseGroup {
//        //get the (current) expense group using the groupId in the Person obj
//        ////get: ref.expense_group.groupId
//    }

    /****Sign up****/
//    fun checkIfUserExist(phoneNum: String): Boolean {
//        //check if this phone num already exists in the database
//        ////search for: ref.users.phoneNum
//    }

    fun addNewUser(user: DBPerson) {
        //add this user to the DB
        ////create: ref.users.phoneNum
    }

    //this method is called when a new user sign up, to put him in a new group
//    fun addNewExpenseGroup(group: DBExpenseGroup) {
//        ////create: ref.expense_group.groupId
//        //where should the groupId be generated???
//    }

    /****Add expense****/
//    fun addNewExpense(phoneNum: String, expense: DBExpense) {
//        // and add the expense object to the expense node
//        ////add to the list in: ref.expense.phoneNum
//        // update user's wallet????
//    }

    /****Add income****/
//    fun addNewIncome(phoneNum: String, income: DBIncome) {
//        // and add the income object to the income node
//        ////add to the list in: ref.income.phoneNum
//        // update user's wallet????
//    }

    /****Invite partner****/
    fun sendInvitationTo(phoneNum: String, invitation: DBInvitation) {
        //add the invitation to the user's invitations
        ////add to the list in: ref.invitation.phoneNum
    }

    /****Transaction history and Report and Home?****/
//    fun getAllExpenses(phoneNum: String): List<DBExpense> {
//        //get a list of all user's expenses
//        ////get the list: ref.expense.phoneNum
//    }

//    fun getAllIncomes(phoneNum: String): List<DBIncome> {
//        //get a list of all user's incomes
//        ////get the list: ref.income.phoneNum
//    }

    /****Invitations****/
//    fun getAllInvitations(phoneNum: String): List<DBInvitation> {
//        //get a list of all user's invitations
//        ////get the list: ref.invitation.phoneNum
//    }

    /****Profile****/
    /***called when any field in Person obj changes
     * (in Wallet screen and when adding new expense or income, etc..)****/
    fun updateUserInfo(phoneNum: String, user: DBPerson){
        //update this user's data
        ////update: ref.users.phoneNum
    }

}