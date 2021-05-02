package com.example.scanningreceiptstest.database

import com.example.scanningreceiptstest.Model.Person
import com.example.scanningreceiptstest.Model.Transaction
import com.google.firebase.database.Exclude

data class DBPerson(
    val phoneNumber: String, val name: String, val groupId: String, val monthlySalary: Double,
    val totalIncome: Double, val savingAmount: Double, val savingWallet: Double
    //,val expenseList: List<String>, val incomeList: List<String>
) {
    constructor() : this("", "", "", 0.0, 0.0, 0.0, 0.0)
}

fun DBPerson.toPerson(): Person {
    /***********/
    //get all expenses and incomes for this person
    // from DB and put it in a transaction list then pass it to the constructor
    val transactions: List<Transaction> = listOf()
    //transactions = database.getAllExpenses(phoneNumber)
    //convert it from List<DBExpense> to List<Expense>

    return Person(
        phoneNumber,
        name,
        groupId,
        monthlySalary,
        totalIncome,
        savingAmount,
        savingWallet,
        transactions
    )
}