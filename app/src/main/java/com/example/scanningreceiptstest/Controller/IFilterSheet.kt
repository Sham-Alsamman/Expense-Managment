package com.example.scanningreceiptstest.Controller

import com.example.scanningreceiptstest.Model.PeriodTransactionFilter
import com.example.scanningreceiptstest.Model.Transaction


interface IFilterSheet {
    fun applyFilterChanges()
}

fun filterByTime(list: List<Transaction>, periodFilter: PeriodTransactionFilter): ArrayList<Transaction> {
    val selectedTransactions = ArrayList<Transaction>()
    for (i in list) {
        if (periodFilter == PeriodTransactionFilter.CurrentMonth) {
            if (inCurrentMonth(i.date)) {
                selectedTransactions.add(i)
            }
        } else if (periodFilter == PeriodTransactionFilter.Last2Months) {
            if (Last2Months(i.date)) {
                selectedTransactions.add(i)
            }
        } else if (periodFilter == PeriodTransactionFilter.Last3Months) {
            if (Last3Months(i.date)) {
                selectedTransactions.add(i)
            }
        } else if (periodFilter == PeriodTransactionFilter.Last4Months) {
            if (Last4Months(i.date)) {
                selectedTransactions.add(i)
            }
        } else if (periodFilter == PeriodTransactionFilter.OneYear) {
            if (OneYear(i.date)) {
                selectedTransactions.add(i)
            }
        }
    }
    return selectedTransactions
}

private fun inCurrentMonth(givenDate: java.util.Date): Boolean {
    val today = java.util.Date()
    return givenDate.month == today.month && givenDate.year == today.year
}

private fun Last2Months(givenDate: java.util.Date): Boolean {
    val today = java.util.Date()
    return givenDate.month > today.month - 2 && givenDate.year == today.year
}

private fun Last3Months(givenDate: java.util.Date): Boolean {
    val today = java.util.Date()
    return givenDate.month > today.month - 3 && givenDate.year == today.year
}

private fun Last4Months(givenDate: java.util.Date): Boolean {
    val today = java.util.Date()
    return givenDate.month > today.month - 4 && givenDate.year == today.year
}

private fun OneYear(givenDate: java.util.Date): Boolean {
    val today = java.util.Date()
    return givenDate.year == today.year
}