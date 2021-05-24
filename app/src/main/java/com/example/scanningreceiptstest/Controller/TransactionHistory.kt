package com.example.scanningreceiptstest.Controller

import android.os.Bundle
import android.view.View
import com.example.scanningreceiptstest.Controller.recyclerViewAdapters.TransactionHistoryAdapter
import com.example.scanningreceiptstest.Model.GroupTransactionFilter
import com.example.scanningreceiptstest.Model.Transaction
import com.example.scanningreceiptstest.R
import com.example.scanningreceiptstest.database.*
import kotlinx.android.synthetic.main.activity_transation_history.*

class TransactionHistory : NavDrawerActivity(), IFilterSheet {

    private val recyclerAdapter = TransactionHistoryAdapter(R.layout.transaction_history_list_item)
    private val filterSheet = BottomSheet_Filter(this)
    private var transactionsList = mutableListOf<Transaction>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transation_history)
        onCreateDrawer()

        transactionRecyclerView.adapter = recyclerAdapter
        getTransactions()
    }

    private fun getTransactions() {
        transactionsList.clear()
        if (filterSheet.groupFilter == GroupTransactionFilter.Group){
            for (id in CURRENT_GROUP?.partners!!){
                database.getAllExpenses(id, ::onExpenseDBResult)
                database.getAllIncomes(id, ::onIncomeDBResult)
            }
        }else {
            database.getAllExpenses(CURRENT_USER!!.phoneNumber, ::onExpenseDBResult)
            database.getAllIncomes(CURRENT_USER!!.phoneNumber, ::onIncomeDBResult)
        }
    }

    private fun onExpenseDBResult(list: List<DBExpense>){
        transactionsList.addAll(list.toExpenseList())

        transactionsList = filterByTime(transactionsList, filterSheet.periodFilter)
        //update the adapter list:
        recyclerAdapter.transactionsList = transactionsList

        //Toast.makeText(this, "expense updated", Toast.LENGTH_SHORT).show()
    }

    private fun onIncomeDBResult(list: List<DBIncome>) {
        transactionsList.addAll(list.toIncomeList())

        transactionsList = filterByTime(transactionsList, filterSheet.periodFilter)
        //update the adapter list:
        recyclerAdapter.transactionsList = transactionsList

        //Toast.makeText(this, "income updated", Toast.LENGTH_SHORT).show()
    }


    fun openFilterSheet(view: View) {
        filterSheet.show(supportFragmentManager, "BottomSheetDialog")
    }

    override fun applyFilterChanges() {
        getTransactions()
    }
}
