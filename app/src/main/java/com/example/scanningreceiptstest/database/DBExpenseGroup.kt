package com.example.scanningreceiptstest.database

import com.example.scanningreceiptstest.Model.ExpenseGroup

data class DBExpenseGroup(
    var groupID: String,
    val partners :MutableList<String> = mutableListOf()
){
    constructor(): this("")
}

fun DBExpenseGroup.toExpenseGroup(): ExpenseGroup {
    return ExpenseGroup(
        groupID,
        partners
    )
}