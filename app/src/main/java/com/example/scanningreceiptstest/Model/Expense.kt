package com.example.scanningreceiptstest.Model

import com.example.scanningreceiptstest.database.DBExpense
import java.util.*


class Expense(
    val date1: Date,
    val amount1: Double,
    val category1: String,
    val name: String,
    val recurrent1: recEnum
) : Transaction( date1, amount1) {

    var recurrent: recEnum = recurrent1
    var vendorName = name
    var category: String = category1
}

/**** Create DBExpense data class in database package
 * contains all fields in Expense class (including Transaction class fields)
 * all fields will be declared in the constructor with val keyword
 * for example, see Invitation class and DBInvitation class
 *
 * create extension methods in both classes to convert from the original class to the DB class and vice versa
 * (see Invitation classes for more details)
 ****/

fun Expense.toExpense() : DBExpense {
    return DBExpense(
        date1,
        amount1,
        category1,
        name,
        recurrent1
    )
}