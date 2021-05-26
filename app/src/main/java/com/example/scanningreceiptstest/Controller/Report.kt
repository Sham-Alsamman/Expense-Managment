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
        database.getAllExpenses(CURRENT_USER!!.phoneNumber, ::ExpensesDBResultIndividual)

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
                    database.getAllExpenses(i, ::ExpensesDBResultGroup)
            }

        } else if (filterSheet.groupFilter == GroupTransactionFilter.Individual) {
            database.getAllExpenses(CURRENT_USER!!.phoneNumber, ::ExpensesDBResultIndividual)
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

        var f = 0.0f

        if (Food > 0.0) {
            f = (((Food / totalPrices) * 10.0).roundToInt() / 10.0).toFloat()

            if(f > 0.0)
                valuesList.add(PieEntry(f, "Food"))
        }

        if (Shopping > 0.0) {
            f = (((Shopping / totalPrices) * 10.0).roundToInt() / 10.0).toFloat()

            if(f > 0.0)
                valuesList.add(PieEntry(f, "Shopping"))
        }

        if (Medical_and_Health > 0.0) {
            f = (((Medical_and_Health / totalPrices) * 10.0).roundToInt() / 10.0).toFloat()

            if (f > 0.0)
                valuesList.add(PieEntry(f, "Medical and Health"))
        }

        if (Education > 0.0) {
            f = (((Education / totalPrices) * 10.0).roundToInt() / 10.0).toFloat()

            if(f > 0.0)
                valuesList.add(PieEntry(f, "Education"))
        }

        if (Housing > 0.0) {
            f = (((Housing / totalPrices) * 10.0).roundToInt() / 10.0).toFloat()

            if(f > 0.0)
                valuesList.add(PieEntry(f, "Housing"))
        }

        if (Entertainment > 0.0) {
            f = (((Entertainment / totalPrices) * 10.0).roundToInt() / 10.0).toFloat()

            if(f > 0.0)
                valuesList.add(PieEntry(f, "Entertainment"))
        }

        if (Transposition > 0.0) {
            f = (((Transposition / totalPrices) * 10.0).roundToInt() / 10.0).toFloat()

            if(f > 0.0)
                valuesList.add(PieEntry(f, "Transposition"))
        }

        if (Other > 0.0) {
            f = (((Other / totalPrices) * 10.0).roundToInt() / 10.0).toFloat()

            if(f > 0.0)
                valuesList.add(PieEntry(f, "Other"))
        }

        println("list len at the end of addValues = " + valuesList.size)
    }

    private fun ExpensesDBResultIndividual(list: List<DBExpense>) {
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

    private fun ExpensesDBResultGroup(list: List<DBExpense>) {
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