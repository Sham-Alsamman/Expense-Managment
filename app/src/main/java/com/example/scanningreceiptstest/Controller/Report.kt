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
import kotlinx.android.synthetic.main.activity_report.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.roundToInt

class Report : NavDrawerActivity(), IFilterSheet {

    lateinit var pieChart: PieChart;
    var valuesList: ArrayList<PieEntry> = ArrayList<PieEntry>();
    var filteredTransactions: ArrayList<Transaction> = ArrayList<Transaction>();
    private val filterSheet = BottomSheet_Filter(this)
    var Food = 0.0
    var Shopping = 0.0
    var Medical_and_Health = 0.0
    var Education = 0.0
    var Housing = 0.0
    var Entertainment = 0.0
    var Transposition = 0.0
    var Other = 0.0

    var count = 0
    var max = CURRENT_GROUP!!.partners.size

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report)
        onCreateDrawer()
        pieChart = findViewById(R.id.piechart);
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

        val x = currentDate.get(0)
        val y = currentDate.get(1)
        val today = "$x$y"
        val res = (CURRENT_USER!!.totalIncome - CURRENT_USER!!.remaining).div(today.toDouble())
        val number2digits: Double = (res.toDouble() * 100.0).roundToInt() / 100.0

        val d = "JD" + number2digits.toString() + " / Day"
        avgValue.text = d

        System.out.println(today)
    }

    fun generate(list: ArrayList<PieEntry>) {
        System.out.println("list to print in generate . size = " + list.size)
        var dataset = PieDataSet(list, ".");
        var pd = PieData(dataset);
        pieChart.data = pd;

        dataset.setColors(
            Color.CYAN,
            Color.LTGRAY,
            Color.BLUE,
            Color.GREEN,
            Color.DKGRAY,
            Color.YELLOW,
            Color.MAGENTA,
            Color.GRAY
        )

        dataset.setValueTextSize(20f)
        pd.setValueTextSize(20f)

        pieChart.getDescription().text = ""; //the text which will be displayed.
        pieChart.setUsePercentValues(true);
        pieChart.getLegend().setEnabled(false);
        pieChart.invalidate()
        pieChart.animateXY(5000, 5000)
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

        System.out.println("last edit = " + filteredTransactions.size)
    }

    private fun addValues(list: List<Transaction>) {
        System.out.println("list len at the start of addValues = " + valuesList.size)

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

        var totalPrices =
            Food.toFloat() + Shopping.toFloat() + Medical_and_Health.toFloat() + Education.toFloat() + Housing.toFloat() + Entertainment.toFloat() + Transposition.toFloat() + Other.toFloat()




        if (Food != 0.0)
            valuesList.add(PieEntry((Food.toFloat() / totalPrices), "Food"))
        if (Shopping != 0.0)
            valuesList.add(PieEntry((Shopping.toFloat() / totalPrices), "Shopping"))
        if (Medical_and_Health != 0.0)
            valuesList.add( PieEntry((Medical_and_Health.toFloat() / totalPrices), "Medical and Health"))
        if (Education != 0.0)
            valuesList.add(PieEntry((Education.toFloat() / totalPrices), "Education"))
        if (Housing != 0.0)
            valuesList.add(PieEntry((Housing.toFloat() / totalPrices), "Housing"))
        if (Entertainment != 0.0)
            valuesList.add(PieEntry((Entertainment.toFloat() / totalPrices), "Entertainment"))
        if (Transposition != 0.0)
            valuesList.add(PieEntry((Transposition.toFloat() / totalPrices), "Transposition"))
        if (Other != 0.0)
            valuesList.add(PieEntry((Other.toFloat() / totalPrices), "Other"))

        System.out.println("list len at the end of addValues = " + valuesList.size)
    }

    private fun ExpensesDBResultFirst(list: List<DBExpense>) {
        System.out.println("list for individual from data base = " + list.size)

        var TransList: ArrayList<Transaction> = ArrayList()
        for (i in list) {
            var e = i.toExpense()
            TransList.add(e as Transaction)
        }

        System.out.println("size before for individual should be 0 = " + filteredTransactions.size)
        filteredTransactions = filterByTime(TransList, filterSheet.periodFilter)
        System.out.println("size after for individual should be > 0 = " + filteredTransactions.size)

        addValues(filteredTransactions)
        generate(valuesList)
    }

    private fun ExpensesDBResult(list: List<DBExpense>) {
        if (count <= max) {

            System.out.println("list from data base = " + list.size)

            var TransList: ArrayList<Transaction> = ArrayList()
            for (i in list) {
                var e = i.toExpense()
                TransList.add(e as Transaction)
            }

            var temp = filterByTime(TransList, filterSheet.periodFilter)
            System.out.println("size before" + filteredTransactions.size)
            filteredTransactions.addAll(temp)
            System.out.println("size after" + filteredTransactions.size)

            System.out.println("user number = " + count)



            count++
            if(count == max){
                System.out.println("user number = "+ count)
                addValues(filteredTransactions)
                generate(valuesList)
            }
        }

    }

}