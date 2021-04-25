package com.example.scanningreceiptstest.Model

import com.example.scanningreceiptstest.database.DBIncome
import java.util.*

class Income(
    date: Date,
    amount: Double,
    name1: String
) : Transaction(date, amount) {
    var name = name1

    fun toDBIncome(): DBIncome {
        return DBIncome(
            date,
            amount,
            name
        )
    }
}


/**** Create DBIncome data class in database package
 * contains all fields in Income class (including Transaction class fields)
 * all fields will be declared in the constructor with val keyword
 * for example, see Invitation class and DBInvitation class
 *
 * create extension methods in both classes to convert from the original class to the DB class and vice versa
 * (see Invitation classes for more details)
 ****/

