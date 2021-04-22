package com.example.scanningreceiptstest.database

import com.example.scanningreceiptstest.Model.Income
import com.example.scanningreceiptstest.Model.Invitation
import java.util.*


data class DBAddIncome(val date: Date,val amount: Double,val name: String)



fun DBAddIncome.toInvitation(): Income {
    return Income(
        date,
        amount,
        name )
}