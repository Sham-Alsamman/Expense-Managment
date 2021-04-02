package com.example.scanningreceiptstest.Model

import java.util.*

public abstract class Transaction(date: Date,amount: Double,notes: String,id : String,category:String) {
    var date: Date=date
    var amount: Double =amount
    var notes: String=notes
    var id : String=id
    var category: String=category

 fun Add (T: Transaction)
    {

    }

}