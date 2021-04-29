package com.example.scanningreceiptstest.Model

import com.example.scanningreceiptstest.database.DBExpenseGroup

class ExpenseGroup(val groupID: String, partners: MutableList<String>) {

    // here i want to declare list of person
    private val _partners: MutableList<String> = mutableListOf()
    var ID: String = "";


    fun addPartners(PhoneNumber: String) {
        // here should search in firebase about the phone number if exist or not
        // if exist we add the person to the partners list
        //add new partners to list of person
        // _Partners.add();
    }

    /**this method is called by Transaction History activity**/
    fun getFilteredTransactionHistory(
        groupFilter: GroupTransactionFilter,
        periodFilter: PeriodTransactionFilter
    ) { // return type list of transaction
        // call getTransaction() mehtod  from person to retrive list of transaction

        //call function filtering() in this class to filter the transaction depend on what user need

        /***
         * if the groupFilter is individual: get expenses and incomes for the current user only
         * else: set the expenses and incomes for all partners
         *
         * then call filtering() and pass the list<transaction> to it, to filter by period (duration)
         *
         * return filtered list
         ***/
    }

    /**this method is called by Report activity**/
    fun getFilteredExpenses(
        groupFilter: GroupTransactionFilter,
        periodFilter: PeriodTransactionFilter
    ) { //returns List<Expense>
        /***
         * if the groupFilter is individual: get expenses for the current user only
         * else: set the expenses for all partners
         * (no need for the income)
         *
         * then call filtering() and pass the list<transaction> to it, to filter by period (duration)
         *
         * return filtered list as List<Expense>
         */
    }

    private fun filtering(
        list: List<Transaction>,
        periodFilter: PeriodTransactionFilter
    ) { // should take two parameter transaction list to filter it and filter to filter list  depend on it

        // filtering the transction list depend on parameter

        //Example to filter list :
        // var month: MutableList<String> = mutableListOf("January", "February", "March")
        // to get the result as list
        // var monthList: MutableList<String> = month.filter { s -> s == "January" } as MutableList<String>

        // return filter list
    }

    fun toDBExpenseGroup(): DBExpenseGroup {
        return DBExpenseGroup(
            groupID,
            _partners
        )
    }

}
