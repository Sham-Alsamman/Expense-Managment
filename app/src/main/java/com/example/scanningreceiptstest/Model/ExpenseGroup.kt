package com.example.scanningreceiptstest.Model

import com.example.scanningreceiptstest.database.*

class ExpenseGroup(val groupID: String, partners: MutableList<String>) {

    // here i want to declare list of person
    private val _partners: MutableList<String> = mutableListOf()
    val partners: List<String>
        get() = _partners

    var ID: String = "";


    fun addPartner(PhoneNumber: String) {
        // here should search in firebase about the phone number if exist or not
        // if exist we add the person to the partners list
        //add new partners to list of person
         _partners.add(PhoneNumber);
    }

    fun toDBExpenseGroup(): DBExpenseGroup {
        return DBExpenseGroup(
            groupID,
            _partners
        )
    }

    companion object {
        fun filterTransactions(
            list: List<Transaction>,
            periodFilter: PeriodTransactionFilter
        ): List<Transaction> { // should take two parameter transaction list to filter it and filter to filter list  depend on it
            val filteredList = mutableListOf<Transaction>()

            // filtering the transaction list depend on parameter
            when (periodFilter) {
                PeriodTransactionFilter.CurrentMonth -> {

                }
                PeriodTransactionFilter.Last2Months -> {

                }
                PeriodTransactionFilter.Last3Months -> {

                }
                PeriodTransactionFilter.Last4Months -> {

                }
                PeriodTransactionFilter.OneYear -> {

                }
            }

            //Example to filter list :
            // var month: MutableList<String> = mutableListOf("January", "February", "March")
            // to get the result as list
            // var monthList: MutableList<String> = month.filter { s -> s == "January" } as MutableList<String>

            // return filter list
            return list
        }
    }

}
