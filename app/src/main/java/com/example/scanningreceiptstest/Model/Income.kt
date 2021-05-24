package com.example.scanningreceiptstest.Model

import com.example.scanningreceiptstest.database.DBIncome
import java.util.*

class Income(
    date: Date,
    amount: Double,
    name1: String
) : Transaction(date, amount) {
    var name = name1

    fun toDBIncome(): DBIncome {
        return DBIncome(
            date,
            amount,
            name
        )
    }
}
