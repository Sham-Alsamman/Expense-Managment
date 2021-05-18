package com.example.scanningreceiptstest.database

import com.example.scanningreceiptstest.Model.Person
import com.example.scanningreceiptstest.Model.Transaction

data class DBPerson(
    val phoneNumber: String, val name: String, val password :String, val groupId: String, val monthlySalary: Double,
    val totalIncome: Double, val savingAmount: Double, val savingWallet: Double, val remaining: Double
) {
    constructor() : this("", "", "","", 0.0, 0.0, 0.0, 0.0, 0.0)
}

fun DBPerson.toPerson(): Person {
    return Person(
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