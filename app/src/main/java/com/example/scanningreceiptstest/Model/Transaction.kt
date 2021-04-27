package com.example.scanningreceiptstest.Model

import java.util.*

abstract class Transaction(
    date: Date,
    amount: Double
) {
    var date: Date = date
    var amount: Double = amount
    lateinit var id: String
}