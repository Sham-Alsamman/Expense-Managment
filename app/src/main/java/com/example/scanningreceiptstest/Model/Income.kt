package com.example.scanningreceiptstest.Model

import java.util.*

public class Income(
    date: Date,
    amount: Double,
    notes: String?,
    category1: String,
    name:String
) : Transaction(date,amount,notes,category1,name) {


}