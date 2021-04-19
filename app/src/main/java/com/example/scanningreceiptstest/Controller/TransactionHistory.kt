package com.example.scanningreceiptstest.Controller

import android.os.Bundle
import com.example.scanningreceiptstest.Controller.recyclerViewAdapters.TransactionHistoryAdapter
import com.example.scanningreceiptstest.Model.Expense
import com.example.scanningreceiptstest.Model.Income
import com.example.scanningreceiptstest.Model.Transaction
import com.example.scanningreceiptstest.Model.recEnum
import com.example.scanningreceiptstest.R
import kotlinx.android.synthetic.main.activity_transation_history.*
import java.sql.Date

class TransactionHistory : NavDrawerActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transation_history)
        onCreateDrawer()

        /***testing***/
        val transactions = listOf(Income(Date.valueOf("2020-3-8"), 25.2,""),
        Expense(Date.valueOf("2005-12-15"), 30.0, "", "shopping",recEnum.None))

        val recyclerAdapter = TransactionHistoryAdapter()
        recyclerAdapter.transactionsList = transactions
        transactionRecyclerView.adapter = recyclerAdapter
    }
}