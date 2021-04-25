package com.example.scanningreceiptstest.database

import com.example.scanningreceiptstest.Model.Income
import com.example.scanningreceiptstest.Model.Invitation
import java.util.*


data class DBIncome(val date: Date, val amount: Double, val name: String){
    constructor(): this(Date(), 0.0, "")
}


fun DBIncome.toIncome(): Income {
    return Income(
        date,
        amount,
        name
    )
}

fun List<DBIncome>.toIncomeList(): List<Income> {
    val incomeList = mutableListOf<Income>()
    for (i in this){
        incomeList.add(i.toIncome())
    }
    return incomeList
}