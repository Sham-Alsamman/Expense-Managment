package com.example.scanningreceiptstest.Model

import java.util.*


public class Expense(
     date1: Date,
     amount1: Double,
     category1: String,
     name:String,
     recurrent1:recEnum
) : Transaction(date1,amount1) {

     var recurrent:recEnum=recurrent1
     var vendorName=name
     var category:String = category1
}