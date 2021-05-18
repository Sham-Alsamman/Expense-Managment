package com.example.scanningreceiptstest.Controller

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.scanningreceiptstest.R
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.example.scanningreceiptstest.Model.Expense
import com.example.scanningreceiptstest.Model.GroupTransactionFilter
import com.example.scanningreceiptstest.Model.PeriodTransactionFilter
import com.example.scanningreceiptstest.Model.Transaction
import com.example.scanningreceiptstest.database.*
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.utils.ColorTemplate
import kotlinx.android.synthetic.main.activity_report.*
import kotlinx.android.synthetic.main.bottomsheet_filter.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.roundToInt

class Report : NavDrawerActivity(), IFilterSheet {

    lateinit var pieChart: PieChart;
    var valuesList : ArrayList<PieEntry> = ArrayList<PieEntry>();

    private val filterSheet = BottomSheet_Filter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report)
        onCreateDrawer()
        pieChart = findViewById(R.id.piechart);
        applyFirstChanges()

        filter_btn.setOnClickListener {
            filterSheet.show(supportFragmentManager, "BottomSheetDialog")
        }

        val a =  "JD" + CURRENT_USER!!.totalIncome.toString()
        totalValue.text = a

        val b = "JD" + CURRENT_USER!!.remaining.toString()
        RemainingValue.text = b

        val sdf = SimpleDateFormat("dd/M/yyyy")
        val currentDate = sdf.format(Date()).toString()

        var x = currentDate.get(0)
        var y = currentDate.get(1)
        var today = "$x$y"
        var res =  (CURRENT_USER!!.totalIncome - CURRENT_USER!!.remaining).div(today.toDouble())
        val number2digits:Double = (res.toDouble() * 100.0).roundToInt() / 100.0

        val d = "JD" + number2digits.toString() + " / Day"
        avgValue.text = d

        System.out.println(today)
    }

    fun generate(list : ArrayList<PieEntry>){

        var dataset = PieDataSet(list,".");

        var pd = PieData(dataset);
        pieChart.data = pd;

        dataset.setColors(Color.CYAN, Color.LTGRAY, Color.BLUE, Color.LTGRAY, Color.DKGRAY, Color.YELLOW, Color.MAGENTA, Color.GRAY)

        dataset.setValueTextSize(20f)
        pd.setValueTextSize(20f)

        pieChart.getDescription().text = ""; //the text which will be displayed.
        pieChart.setUsePercentValues(true);
        pieChart.getLegend().setEnabled(false);
        pieChart.invalidate()
    }

    fun applyFirstChanges(){
            Database.getAllExpenses(CURRENT_USER!!.phoneNumber, ::ExpensesDBResult)
    }

    override fun applyFilterChanges() {
        if (filterSheet.groupFilter == GroupTransactionFilter.Group) {
            for (i in CURRENT_GROUP!!.partners)
                Database.getAllExpenses(i, ::ExpensesDBResult)
        }
        else if (filterSheet.groupFilter == GroupTransactionFilter.Individual) {
            Database.getAllExpenses(CURRENT_USER!!.phoneNumber, ::ExpensesDBResult)
        }
    }

    private fun addValues(list: List<Transaction>){
        valuesList = ArrayList<PieEntry>();

        var Food = 0.0
        var Shopping = 0.0
        var Medical_and_Health = 0.0
        var Education = 0.0
        var Housing = 0.0
        var Entertainment = 0.0
        var Transposition = 0.0
        var Other = 0.0

        for(i in list){
            i as Expense
            if( i.category == "Food" )
                Food += i.amount
            else if( i.category == "Shopping")
                Shopping += i.amount
            else if( i.category == "Medical and Health")
                Medical_and_Health += i.amount
            else if( i.category == "Education")
                Education += i.amount
            else if( i.category == "Housing")
                Housing += i.amount
            else if( i.category == "Entertainment")
                Entertainment += i.amount
            else if( i.category == "Transposition")
                Transposition += i.amount
            else if( i.category == "Other")
                Other += i.amount
        }

        var totalPrices = Food.toFloat() + Shopping.toFloat() + Medical_and_Health.toFloat() + Education.toFloat() + Housing.toFloat() + Entertainment.toFloat() + Transposition.toFloat() + Other.toFloat()

        if(Food != 0.0)
            valuesList.add( PieEntry((Food.toFloat()/totalPrices), "Food"))
        if(Shopping != 0.0)
            valuesList.add( PieEntry((Shopping.toFloat()/totalPrices), "Shopping"))
        if(Medical_and_Health != 0.0)
            valuesList.add( PieEntry((Medical_and_Health.toFloat()/totalPrices), "Medical and Health"))
        if(Education != 0.0)
            valuesList.add( PieEntry((Education.toFloat()/totalPrices), "Education"))
        if(Housing != 0.0)
            valuesList.add( PieEntry((Housing.toFloat()/totalPrices), "Housing"))
        if(Entertainment != 0.0)
            valuesList.add( PieEntry((Entertainment.toFloat()/totalPrices), "Entertainment"))
        if(Transposition != 0.0)
            valuesList.add( PieEntry((Transposition.toFloat()/totalPrices), "Transposition"))
        if(Other != 0.0)
            valuesList.add( PieEntry((Other.toFloat()/totalPrices), "Other"))


        generate(valuesList)
    }

    private fun ExpensesDBResult(list: List<DBExpense>) {
        var TransList: ArrayList<Transaction> = ArrayList()
        for (i in list) {
            var e = i.toExpense()
            TransList.add(e as Transaction)
        }

        var filteredTransactions = filterByTime(TransList, filterSheet.periodFilter)
        addValues(filteredTransactions)
    }
}