package com.example.scanningreceiptstest.database

interface IDatabase {

    fun getUser(phoneNum: String, onDataRetrieved: (DBPerson) -> Unit)
    fun getExpenseGroup(groupId: String, onDataRetrieved: (DBExpenseGroup) -> Unit)

    fun checkPassword(Phone: String, Pass: String, onDataRetrieved: (Boolean) -> Unit)
    fun checkIfUserExist(phoneNum: String, onDataRetrieved: (Boolean) -> Unit)

    fun addNewUser(user: DBPerson, onAddedSuccessfully: (Boolean) -> Unit)
    fun addNewExpenseGroup(group: DBExpenseGroup): DBExpenseGroup
    fun addNewExpense(phoneNum: String, expense: DBExpense)
    fun addNewIncome(phoneNum: String, income: DBIncome)
    fun sendInvitation(invitation: DBInvitation)

    fun getAllExpenses(phoneNum: String, onDataRetrievedFun: (list: List<DBExpense>) -> Unit)
    fun getAllIncomes(phoneNum: String, onDataRetrievedFun: (list: List<DBIncome>) -> Unit)
    fun getAllInvitations(
        phoneNum: String,
        onDataRetrievedFun: (list: List<DBInvitation>) -> Unit
    )

    fun updateUserInfo(user: DBPerson)
    fun updateInvitation(phoneNum: String, invitation: DBInvitation)
    fun updateExpenseGroup(group: DBExpenseGroup)

    fun deleteExpenseGroup(groupId: String)
}