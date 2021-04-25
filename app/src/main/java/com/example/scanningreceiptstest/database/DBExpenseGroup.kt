package com.example.scanningreceiptstest.database

import com.example.scanningreceiptstest.Model.ExpenseGroup
import com.example.scanningreceiptstest.Model.Person

data class DBExpenseGroup(
    var groupID: String,
    val _Partners :MutableList<Person> = mutableListOf<Person>()
){
    constructor(): this("")
}

fun DBExpenseGroup.toExpenseGroup(): ExpenseGroup {
    return ExpenseGroup(
        groupID,
        _Partners
    )
}