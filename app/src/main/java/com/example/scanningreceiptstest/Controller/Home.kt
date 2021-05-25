package com.example.scanningreceiptstest.Controller

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.example.scanningreceiptstest.Controller.recyclerViewAdapters.TransactionHistoryAdapter
import com.example.scanningreceiptstest.Model.Transaction
import com.example.scanningreceiptstest.R
import com.example.scanningreceiptstest.database.*
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_home.noData_home

class Home : NavDrawerActivity() {
    private val recyclerAdapter = TransactionHistoryAdapter(R.layout.transation_history_home_item)
    private var transactionsList = mutableListOf<Transaction>()


    private var mScanFab: FloatingActionButton? = null
    private var mManuallyFab: FloatingActionButton? = null
    private var mAddFab: FloatingActionButton? = null


    // These are taken to make visible and invisible along
    // with FABs
    private var addManuallyActionText: TextView? = null
    private var addScanActionText: TextView? = null

    // to check whether sub FAB buttons are visible or not.
    private var isAllFabsVisible: Boolean? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        onCreateDrawer()

        val totalB=findViewById<TextView>(R.id.totalBalance)

        totalB.text = CURRENT_USER!!.remaining.toString()+" JD"

        lastRecord.adapter = recyclerAdapter
        getTransactions()

        // Register all the FABs with their IDs
        // This FAB button is the Parent
        mScanFab = findViewById(R.id.scan_fab)
        mManuallyFab = findViewById(R.id.manually_fab)
        mAddFab = findViewById(R.id.add_fab)


        addManuallyActionText = findViewById(R.id.addManuallyActionText)
        addScanActionText = findViewById(R.id.addScanActionText)

        hideFabs()

        mAddFab!!.setOnClickListener {

            if (!isAllFabsVisible!!) {
                mScanFab!!.show()
                mManuallyFab!!.show()
                addManuallyActionText!!.visibility = View.VISIBLE
                addScanActionText!!.visibility = View.VISIBLE

                isAllFabsVisible = true
            } else {
                hideFabs()
            }
        }



        mScanFab!!.setOnClickListener {
            val intent = Intent(this, Scan::class.java)
            startActivity(intent)
            hideFabs()
        }

        mManuallyFab!!.setOnClickListener {
            val intent = Intent(this, AddManually::class.java)
            startActivity(intent)
            hideFabs()
        }
    }

    private fun hideFabs() {
        mScanFab!!.hide()
        mManuallyFab!!.hide()
        addManuallyActionText!!.visibility = View.GONE
        addScanActionText!!.visibility = View.GONE

        isAllFabsVisible = false
    }

    fun openAddIncome(view: View) {
        val intent = Intent(this, AddIncome::class.java)
        startActivity(intent)
    }


    private fun getTransactions() {
        transactionsList.clear()
        database.getAllExpenses(CURRENT_USER!!.phoneNumber, ::onExpenseDBResult)
        database.getAllIncomes(CURRENT_USER!!.phoneNumber, ::onIncomeDBResult)
    }

    private fun onExpenseDBResult(list: List<DBExpense>) {
        transactionsList.addAll(list.toExpenseList())
        if (transactionsList.isEmpty()) {
            noData_home.visibility = View.VISIBLE
        }else {
            noData_home.visibility = View.GONE
            //update the adapter list:
            recyclerAdapter.transactionsList = transactionsList
        }
    }

    private fun onIncomeDBResult(list: List<DBIncome>) {
        transactionsList.addAll(list.toIncomeList())
        if (transactionsList.isEmpty()) {
            noData_home.visibility = View.VISIBLE
        }else {
            noData_home.visibility = View.GONE
            //update the adapter list:
            recyclerAdapter.transactionsList = transactionsList
        }
    }
}