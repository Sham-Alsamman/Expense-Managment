package com.example.scanningreceiptstest.Model

import com.example.scanningreceiptstest.database.DBExpense
import java.util.*


class Expense(
    date1: Date,
    amount1: Double,
    category1: String,
    name: String,
) : Transaction( date1, amount1) {

    var vendorName = name
    var category: String = category1

    fun toDBExpense() : DBExpense {
        return DBExpense(
            date,
            amount,
            category,
            vendorName
        )
    }
}