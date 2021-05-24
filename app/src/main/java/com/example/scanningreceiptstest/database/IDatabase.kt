package com.example.scanningreceiptstest.database

interface IDatabase {
    /****Login*****/
    //this parameter is the function to call when the data changes
    fun getUser(phoneNum: String, onDataRetrieved: (DBPerson) -> Unit)
    fun getExpenseGroup(groupId: String, onDataRetrieved: (DBExpenseGroup) -> Unit)
    fun checkPassword(Phone: String, Pass: String, onDataRetrieved: (Boolean) -> Unit)

    /****Sign up****/
    fun checkIfUserExist(phoneNum: String, onDataRetrieved: (Boolean) -> Unit)
    fun addNewUser(user: DBPerson, onAddedSuccessfully: (Boolean) -> Unit)

    //this method is called when a new user sign up, to put him in a new group
    fun addNewExpenseGroup(group: DBExpenseGroup): DBExpenseGroup

    /****Add expense****/
    fun addNewExpense(phoneNum: String, expense: DBExpense)

    /****Add income****/
    fun addNewIncome(phoneNum: String, income: DBIncome)

    /****Invite partner****/
    fun sendInvitation(invitation: DBInvitation)

    /****Transaction history and Report and Home?****/
    fun getAllExpenses(phoneNum: String, onDataRetrievedFun: (list: List<DBExpense>) -> Unit)
    fun getAllIncomes(phoneNum: String, onDataRetrievedFun: (list: List<DBIncome>) -> Unit)

    /****Invitations****/
    //this parameter is the function to call when the data changes
    fun getAllInvitations(
        phoneNum: String,
        onDataRetrievedFun: (list: List<DBInvitation>) -> Unit
    )

    /****Profile****/
    //called when any field in Person obj changes
    fun updateUserInfo(user: DBPerson)
    fun updateInvitation(phoneNum: String, invitation: DBInvitation)
    fun updateExpenseGroup(group: DBExpenseGroup)
    fun deleteExpenseGroup(groupId: String)
}