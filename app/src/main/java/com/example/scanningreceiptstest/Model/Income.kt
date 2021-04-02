package com.example.scanningreceiptstest.Model

import java.util.*

public class Income(
     date: Date,
     amount: Double,
     notes: String,
     id: String,
     category1: String,
    var name: String
) : Transaction(date,amount,notes,id,category1) {


}