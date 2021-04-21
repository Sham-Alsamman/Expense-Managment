package com.example.scanningreceiptstest.Model

import java.util.*


class Expense(
    date1: Date,
    amount1: Double,
    category1: String,
    name: String,
    recurrent1: recEnum
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