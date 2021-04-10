package com.example.scanningreceiptstest.Model

import java.util.*

public abstract class Transaction(
    date: Date,
    amount: Double,
    notes: String?, /*******Delete?*******/
    category: String,
    name: String
) {
    var date: Date = date
    var amount: Double = amount
    var notes: String? = notes
    lateinit var id: String
    var category: String? = category
    var name: String = name

    fun Add(T: Transaction) {

    }

}