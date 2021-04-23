package com.example.scanningreceiptstest.database

import com.example.scanningreceiptstest.Model.Expense
import com.example.scanningreceiptstest.Model.recEnum
import java.util.*

data class DBExpense(
    val date1: Date,
    val amount1: Double,
    val category1: String,
    val name: String,
    val recurrent1: recEnum
)

fun DBExpense.toExpense() : Expense{
    return Expense(
        date1,
        amount1,
        category1,
        name,
        recurrent1
    )
}

