package com.example.scanningreceiptstest.Model

import com.example.scanningreceiptstest.database.CURRENT_USER
import com.example.scanningreceiptstest.database.DBPerson
import java.io.Serializable;

class Person(userName: String, phoneNum: String, password: String) : Serializable {

    constructor(
        phoneNum: String,
        userName: String,
        Password: String,
        groupId: String,
        monthlySal: Double,
        totalIncome: Double, // total income for current month
        savingAmount: Double, // saving rate
        savingWallet: Double, // saving amount + remaining from previous months
        remaining: Double // the remaining from totalIncome for current month (after adding expenses)
    )
            : this(userName, phoneNum, Password) {
        this.groupId = groupId
        this.monthlySalary = monthlySal
        this.totalIncome = totalIncome
        this.savingAmount = savingAmount
        this.savingWallet = savingWallet
        this.remaining = remaining
    }

    var name: String = userName
    var password: String = password

    // the phone number is the ID of person
    var phoneNumber: String = phoneNum
    var groupId: String = ""
    var monthlySalary: Double = 0.0
        set(value) {
            if (value >= 0)
                field = value
        }
    var remaining: Double = 0.0
        set(value) {
            if (value >= 0)
                field = value
        }
    var totalIncome: Double = 0.0
        set(value) {
            if (value >= 0)
                field = value
        }
    var savingAmount: Double = 0.0
        set(value) {
            if (value >= 0)
                field = value
        }
    var savingWallet: Double = 0.0
        set(value) {
            if (value >= 0)
                field = value
        }


    fun changeGroup(newGroupId: String) {
        groupId = newGroupId
    }

    fun addExpenseIfPossible(expense: Double): Boolean {
        if (expense > 0 && remaining - expense >= 0) {
            remaining -= expense
            return true
        }
        return false
    }

    fun canWithdrawFromSavings(expense: Double): Boolean {
        return expense > 0 && (remaining + savingWallet) - expense >= 0
    }

    fun withdrawFromSaving(expense: Double) {
        if (canWithdrawFromSavings(expense)) {
            val amount = expense - remaining
            remaining = 0.0
            savingWallet -= amount
        }
    }

    fun addIncome(income: Double) {
        if (income > 0) {
            totalIncome += income
            remaining += income
        }
    }

    fun toDBPerson(): DBPerson {
        return DBPerson(
            phoneNumber,
            name,
            password,
            groupId,
            monthlySalary,
            totalIncome,
            savingAmount,
            savingWallet,
            remaining
        )
    }

    fun atEndOfMonth() {
        savingWallet += remaining
        remaining = 0.0
        totalIncome = 0.0
    }

    fun addSalaryAndCalculateSaving() {
        atEndOfMonth()
        addIncome(monthlySalary)

        val saving = monthlySalary * (savingAmount / 100)
        remaining -= saving
        savingWallet += saving
    }
}