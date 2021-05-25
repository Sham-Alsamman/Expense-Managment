package com.example.scanningreceiptstest.database

import com.example.scanningreceiptstest.Model.Expense
import java.util.*

data class DBExpense(
    val date: Date,
    val amount: Double,
    val category: String,
    val vendorName: String
) {
    constructor() : this(Date(), 0.0, "", "")
}

fun DBExpense.toExpense(): Expense {
    return Expense(
        date,
        amount,
        category,
        vendorName
    )
}

fun List<DBExpense>.toExpenseList(): List<Expense> {
    val expenseList = mutableListOf<Expense>()
    for (i in this) {
        expenseList.add(i.toExpense())
    }
    return expenseList
}

