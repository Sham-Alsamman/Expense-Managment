package com.example.scanningreceiptstest.Controller

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.scanningreceiptstest.Controller.recyclerViewAdapters.TransactionHistoryAdapter
import com.example.scanningreceiptstest.Model.*
import com.example.scanningreceiptstest.R
import com.example.scanningreceiptstest.database.*
import kotlinx.android.synthetic.main.activity_transation_history.*
import java.sql.Date

class TransactionHistory : NavDrawerActivity(), IFilterSheet {

    private val recyclerAdapter = TransactionHistoryAdapter()
    private val filterSheet = BottomSheet_Filter(this)
    private var transactionsList = mutableListOf<Transaction>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transation_history)
        onCreateDrawer()

        transactionRecyclerView.adapter = recyclerAdapter

        getTransactions()

//        /***testing***/
//        val transactions = listOf(
//            Income(Date.valueOf("2020-3-8"), 25.2, "salary"),
//            Expense(Date.valueOf("2005-12-15"), 30.0, "category1", "shopping", recEnum.None)
//        )

//        val e = Expense(java.util.Date(),0.0, "cat", "vendor", recEnum.None)
//        val e2 = Expense(java.util.Date(),5.0, "cat2", "vendor2", recEnum.None)

//        val i = Income(java.util.Date(), 200.0, "salary")
//        val i2 = Income(java.util.Date(), 270.0, "bonus")

    }

    private fun getTransactions() {
        transactionsList.clear()
        if (filterSheet.groupFilter == GroupTransactionFilter.Group){
            for (id in CURRENT_GROUP?.partners!!){
                Database.getAllExpenses(id, ::onExpenseDBResult)
                Database.getAllIncomes(id, ::onIncomeDBResult)
            }
        }else {
            Database.getAllExpenses("+123456789", ::onExpenseDBResult)
            Database.getAllIncomes("+123456789", ::onIncomeDBResult)
        }
    }

    private fun onExpenseDBResult(list: List<DBExpense>){
        transactionsList.addAll(list.toExpenseList())
        /****filter******/
//        transactionsList =
//            ExpenseGroup.filterTransactions(transactionsList, /*filterSheet.periodFilter*/PeriodTransactionFilter.OneYear).toMutableList()
//        //update the adapter list:
        recyclerAdapter.transactionsList = transactionsList

        Toast.makeText(this, "expense updated", Toast.LENGTH_SHORT).show()
    }

    private fun onIncomeDBResult(list: List<DBIncome>) {
        transactionsList.addAll(list.toIncomeList())
        /****filter******/
//        transactionsList =
//            ExpenseGroup.filterTransactions(transactionsList, filterSheet.periodFilter).toMutableList()
//        //update the adapter list:
        recyclerAdapter.transactionsList = transactionsList

        Toast.makeText(this, "income updated", Toast.LENGTH_SHORT).show()
    }


    fun openFilterSheet(view: View) {
        filterSheet.show(supportFragmentManager, "BottomSheetDialog")
    }

    override fun applyFilterChanges() {
        getTransactions()
    }
}
