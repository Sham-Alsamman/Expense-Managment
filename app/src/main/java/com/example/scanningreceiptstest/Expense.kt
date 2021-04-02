package com.example.scanningreceiptstest

import android.os.Parcel
import android.os.Parcelable
import java.util.*


public class Expense(
     date1: Date,
     amount1: Double,
     notes1: String,
     id1: String,
     category1: String,
    var vendorName: String,

) : Transaction(date1,amount1,notes1,id1,category1) {



}