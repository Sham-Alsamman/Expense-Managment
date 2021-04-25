package com.example.scanningreceiptstest.database

import com.example.scanningreceiptstest.Model.Expense
import com.example.scanningreceiptstest.Model.Income
import com.example.scanningreceiptstest.Model.recEnum
import java.lang.Exception
import java.util.*
import kotlin.math.exp

data class DBExpense(
    val date: Date,
    val amount: Double,
    val category: String,
    val vendorName: String,
    val recurrent: recEnum
) {
    constructor() : this(Date(), 0.0, "", "", recEnum.None)
}

fun DBExpense.toExpense(): Expense {
    return Expense(
        date,
        amount,
        category,
        vendorName,
        recurrent
    )
}

fun List<DBExpense>.toExpenseList(): List<Expense> {
    val expenseList = mutableListOf<Expense>()
    for (i in this) {
        expenseList.add(i.toExpense())
    }
    return expenseList
}

