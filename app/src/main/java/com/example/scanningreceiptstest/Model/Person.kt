package com.example.scanningreceiptstest.Model

import com.example.scanningreceiptstest.database.DBPerson

class Person(userName: String, phoneNum: String) {

    constructor(
        phoneNum: String,
        userName: String,
        groupId: String,
        monthlySal: Double,
        totalIncome: Double,
        savingAmount: Double,
        savingWallet: Double,
        transactions: List<Transaction>
    )
            : this(userName, phoneNum) {
        this.groupId = groupId
        this.monthlySalary = monthlySal
        this.totalIncome = totalIncome
        this.savingAmount = savingAmount
        this.savingWallet = savingWallet
        this._transactions = transactions as MutableList<Transaction>
    }

    var name: String = userName

    // the phone number is the ID of person
    var phoneNumber: String = phoneNum

    var groupId: String = ""  //should be read only (val), auto value??

    var monthlySalary: Double = 0.0
        set(value) { //instead of setMonthlySalary() method
            if (value >= 0)
                field = value
        }

    var totalIncome: Double = 0.0 // should be always positive num??
    var savingAmount: Double = 0.0
        set(value) { // instead of setSavingAmount() method
            if (value >= 0)
                field = value
        }

    var savingWallet: Double = 0.0
        set(value) {
            if (value >= 0)
                field = value
        }
    private var _transactions = mutableListOf<Transaction>()

    /**instead of getTransactions method**/
    val transactions: List<Transaction>
        get() = _transactions


    //not here?
    fun acceptInvitation(homeId: String) {

    }

    //not here?
    fun receiveInvite(homeId: String) {

    }

    fun addExpense(expense: Expense) {
        _transactions.add(expense)
        /****edit total***/
    }

    fun addIncome(income: Income) {
        incrementIncome(income)
        _transactions.add(income)
    }

    private fun incrementIncome(income: Income) {
        totalIncome += income.amount
    }

    fun toDBPerson(): DBPerson {
        return DBPerson(
            phoneNumber,
            name,
            groupId,
            monthlySalary,
            totalIncome,
            savingAmount,
            savingWallet,
        )
    }

}