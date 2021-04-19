package com.example.scanningreceiptstest.Model

import java.util.*

public class Income(
    date: Date,
    amount: Double,
    name1:String
) : Transaction(date,amount) {

     var name=name1

}