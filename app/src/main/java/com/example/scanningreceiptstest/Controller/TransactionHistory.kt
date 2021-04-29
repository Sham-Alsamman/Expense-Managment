package com.example.scanningreceiptstest.Controller

import android.os.Bundle
import com.example.scanningreceiptstest.Controller.recyclerViewAdapters.TransactionHistoryAdapter
import com.example.scanningreceiptstest.Model.Expense
import com.example.scanningreceiptstest.Model.Income
import com.example.scanningreceiptstest.Model.Transaction
import com.example.scanningreceiptstest.Model.recEnum
import com.example.scanningreceiptstest.R
import com.example.scanningreceiptstest.database.CURRENT_USER
import com.example.scanningreceiptstest.database.DBExpense
import com.example.scanningreceiptstest.database.Database
import com.example.scanningreceiptstest.database.toExpenseList
import kotlinx.android.synthetic.main.activity_transation_history.*
import java.sql.Date

class TransactionHistory : NavDrawerActivity() {

    private val recyclerAdapter = TransactionHistoryAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transation_history)
        onCreateDrawer()

//        /***testing***/
//        val transactions = listOf(
//            Income(Date.valueOf("2020-3-8"), 25.2, "salary"),
//            Expense(Date.valueOf("2005-12-15"), 30.0, "category1", "shopping", recEnum.None)
//        )

//        val e = Expense(java.util.Date(),0.0, "cat", "vendor", recEnum.None)
//        val e2 = Expense(java.util.Date(),5.0, "cat2", "vendor2", recEnum.None)
//        Database.addNewExpense("+123456789", e.toDBExpense())
//        Database.addNewExpense("+123456789", e2.toDBExpense())
        Database.getAllExpenses("+123456789", ::onDBResult)

        //transaction history: get all/filtered expenses and income (group filter, period filter)
        //report: get all/filtered expenses only (group filter, period filter)


        transactionRecyclerView.adapter = recyclerAdapter
    }

    private fun onDBResult(list: List<DBExpense>){
        //update the adapter list:
        recyclerAdapter.transactionsList = list.toExpenseList()
    }
}