package com.example.scanningreceiptstest.database

import com.example.scanningreceiptstest.Model.InvitationStatus
import com.example.scanningreceiptstest.Model.Person

data class ExpenseGroup(
    val groupID: String,
    val _Partners :MutableList<Person> = mutableListOf<Person>()

)

fun ExpenseGroup.toExpenseGroup(): ExpenseGroup {
    return ExpenseGroup(
        groupID,
        _Partners
    )
}