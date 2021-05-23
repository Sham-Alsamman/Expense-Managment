package com.example.scanningreceiptstest.Controller

import android.graphics.Color
import android.os.Bundle
import com.example.scanningreceiptstest.Model.Expense
import com.example.scanningreceiptstest.Model.GroupTransactionFilter
import com.example.scanningreceiptstest.Model.Transaction
import com.example.scanningreceiptstest.R
import com.example.scanningreceiptstest.database.*
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import kotlinx.android.synthetic.main.activity_report.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.roundToInt


class Report : NavDrawerActivity(), IFilterSheet {

    private lateinit var pieChart: PieChart
    private var valuesList: ArrayList<PieEntry> = ArrayList()
    private var filteredTransactions: ArrayList<Transaction> = ArrayList()
    private val filterSheet = BottomSheet_Filter(this)
    private var Food = 0.0
    private var Shopping = 0.0
    private var Medical_and_Health = 0.0
    private var Education = 0.0
    private var Housing = 0.0
    private var Entertainment = 0.0
    private var Transposition = 0.0
    private var Other = 0.0

    private var count = 0
    private var max = CURRENT_GROUP!!.partners.size

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report)
        onCreateDrawer()
        pieChart = findViewById(R.id.piechart)
        Database.getAllExpenses(CURRENT_USER!!.phoneNumber, ::ExpensesDBResultFirst)

        filter_btn.setOnClickListener {
            filterSheet.show(supportFragmentManager, "BottomSheetDialog")
        }

        val a = "JD" + CURRENT_USER!!.totalIncome.toString()
        totalValue.text = a

        val b = "JD" + CURRENT_USER!!.remaining.toString()
        RemainingValue.text = b

        val sdf = SimpleDateFormat("dd/M/yyyy")
        val currentDate = sdf.format(Date()).toString()

        val x = currentDate[0]
        val y = currentDate[1]
        val today = "$x$y"
        val res = (CURRENT_USER!!.totalIncome - CURRENT_USER!!.remaining).div(today.toDouble())
        val number2digits: Double = (res * 100.0).roundToInt() / 100.0

        val d = "JD" + number2digits.toString() + " / Day"
        avgValue.text = d

        println(today)
    }

    private fun generate(list: ArrayList<PieEntry>) {
        println("list to print in generate . size = " + list.size)
        val dataset = PieDataSet(list, ".")
        val pd = PieData(dataset)
        pieChart.data = pd

        dataset.setColors(
            Color.rgb(153, 204, 255),
            Color.rgb(153, 153, 255),
            Color.rgb(153, 255, 255),
            Color.rgb(153, 255, 153),
            Color.rgb(255, 204, 153),
            Color.rgb(255, 255, 153),
            Color.rgb(255, 153, 153),
            Color.rgb(255, 0, 127),
        )

        dataset.valueTextSize = 20f
        pd.setValueTextSize(20f)
        pd.setValueFormatter(PercentFormatter())
        pieChart.description.text = "" //the text which will be displayed.
        pieChart.setUsePercentValues(true)
        pieChart.legend.isEnabled = false
        pieChart.invalidate()
        pieChart.animateXY(5000, 5000)
        pieChart.setEntryLabelColor(Color.BLACK)
    }

    override fun applyFilterChanges() {
        filteredTransactions.clear()
        valuesList.clear()
        Food = 0.0
        Shopping = 0.0
        Medical_and_Health = 0.0
        Education = 0.0
        Housing = 0.0
        Entertainment = 0.0
        Transposition = 0.0
        Other = 0.0

        if (filterSheet.groupFilter == GroupTransactionFilter.Group) {
            count = 0
            for (i in CURRENT_GROUP!!.partners) {
                    Database.getAllExpenses(i, ::ExpensesDBResult)
            }

        } else if (filterSheet.groupFilter == GroupTransactionFilter.Individual) {
            Database.getAllExpenses(CURRENT_USER!!.phoneNumber, ::ExpensesDBResultFirst)
        }

        println("last edit = " + filteredTransactions.size)
    }

    private fun addValues(list: List<Transaction>) {
        println("list len at the start of addValues = " + valuesList.size)

        for (i in list) {
            i as Expense
            if (i.category == "Food")
                Food += i.amount
            else if (i.category == "Shopping")
                Shopping += i.amount
            else if (i.category == "Medical and Health")
                Medical_and_Health += i.amount
            else if (i.category == "Education")
                Education += i.amount
            else if (i.category == "Housing")
                Housing += i.amount
            else if (i.category == "Entertainment")
                Entertainment += i.amount
            else if (i.category == "Transposition")
                Transposition += i.amount
            else if (i.category == "Other")
                Other += i.amount
        }

        val totalPrices =
            Food.toFloat() + Shopping.toFloat() + Medical_and_Health.toFloat() + Education.toFloat() + Housing.toFloat() + Entertainment.toFloat() + Transposition.toFloat() + Other.toFloat()

        //(res * 100.0).roundToInt() / 100.0

        var p = PieEntry((Food.toFloat() / totalPrices), "Food")
        if (Food != 0.0)
            valuesList.add(PieEntry((((Food / totalPrices) * 10.0).roundToInt() / 10.0).toFloat(), "Food"))
        if (Shopping != 0.0)
            valuesList.add(
                PieEntry(
                    (((Shopping / totalPrices) * 10.0).roundToInt() / 10.0).toFloat(),
                    "Shopping"
                )
            )
        if (Medical_and_Health != 0.0)
            valuesList.add(
                PieEntry(
                    (((Medical_and_Health / totalPrices) * 10.0).roundToInt() / 10.0).toFloat(),
                    "Medical and Health"
                )
            )
        if (Education != 0.0)
            valuesList.add(
                PieEntry(
                    (((Education / totalPrices) * 10.0).roundToInt() / 10.0).toFloat(),
                    "Education"
                )
            )
        if (Housing != 0.0)
            valuesList.add(
                PieEntry(
                    (((Housing / totalPrices) * 10.0).roundToInt() / 10.0).toFloat(),
                    "Housing"
                )
            )
        if (Entertainment != 0.0)
            valuesList.add(
                PieEntry(
                    (((Entertainment / totalPrices) * 10.0).roundToInt() / 10.0).toFloat(),
                    "Entertainment"
                )
            )
        if (Transposition != 0.0)
            valuesList.add(
                PieEntry(
                    (((Transposition / totalPrices) * 10.0).roundToInt() / 10.0).toFloat(),
                    "Transposition"
                )
            )
        if (Other != 0.0)
            valuesList.add(PieEntry((((Other / totalPrices) * 10.0).roundToInt() / 10.0).toFloat(), "Other"))

        println("list len at the end of addValues = " + valuesList.size)
    }

    private fun ExpensesDBResultFirst(list: List<DBExpense>) {
        println("list for individual from data base = " + list.size)

        val TransList: ArrayList<Transaction> = ArrayList()
        for (i in list) {
            val e = i.toExpense()
            TransList.add(e as Transaction)
        }

        println("size before for individual should be 0 = " + filteredTransactions.size)
        filteredTransactions = filterByTime(TransList, filterSheet.periodFilter)
        println("size after for individual should be > 0 = " + filteredTransactions.size)

        addValues(filteredTransactions)
        generate(valuesList)
    }

    private fun ExpensesDBResult(list: List<DBExpense>) {
        if (count <= max) {

            println("list from data base = " + list.size)

            val TransList: ArrayList<Transaction> = ArrayList()
            for (i in list) {
                val e = i.toExpense()
                TransList.add(e as Transaction)
            }

            val temp = filterByTime(TransList, filterSheet.periodFilter)
            println("size before" + filteredTransactions.size)
            filteredTransactions.addAll(temp)
            println("size after" + filteredTransactions.size)

            println("user number = " + count)

            count++
            if(count == max){
                println("user number = " + count)
                addValues(filteredTransactions)
                generate(valuesList)
            }
        }
    }
}