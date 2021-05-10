package com.example.scanningreceiptstest.Controller

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import com.example.scanningreceiptstest.R
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.example.scanningreceiptstest.Model.Expense
import com.example.scanningreceiptstest.Model.GroupTransactionFilter
import com.example.scanningreceiptstest.Model.Transaction
import com.example.scanningreceiptstest.database.CURRENT_GROUP
import com.example.scanningreceiptstest.database.CURRENT_USER
import com.example.scanningreceiptstest.database.DBExpense
import com.example.scanningreceiptstest.database.Database
import kotlinx.android.synthetic.main.activity_report.*
import kotlinx.android.synthetic.main.bottomsheet_filter.*

class Report : NavDrawerActivity(), IFilterSheet {

    lateinit var pieChart: PieChart;
    var valuesList = ArrayList<PieEntry>();
    private val filterSheet = BottomSheet_Filter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report)
        onCreateDrawer()

//
//        pieChart = findViewById(R.id.piechart);
//
//        valuesList.add(PieEntry(40f, "Jan"));
//        valuesList.add(PieEntry(60f, "Feb"));
//

        filter_btn.setOnClickListener {
            filterSheet.show(supportFragmentManager, "BottomSheetDialog")
        }

//        generate(valuesList)
    }

    fun generate(values : ArrayList<PieEntry>){

        pieChart.setUsePercentValues(true);


        var dataset = PieDataSet(values,".");

        var pd = PieData(dataset);
        pieChart.setData (pd);

        dataset.setColors( Color.CYAN, Color.LTGRAY, Color.BLUE)
        dataset.setValueTextSize(20f)
        pd.setValueTextSize(20f)


        pieChart.getDescription().text = ""; //the text which will be displayed.
    }

    override fun applyFilterChanges() {

        if (filterSheet.groupFilter == GroupTransactionFilter.Group) {

            for(i in CURRENT_GROUP!!.partners)
                Database.getAllExpenses(i, ::ExpensesDBResult)

        } else if (filterSheet.groupFilter == GroupTransactionFilter.Individual) {

            Database.getAllExpenses(CURRENT_USER!!.phoneNumber, ::ExpensesDBResult)
        }


        generate(valuesList)
    }

    private fun ExpensesDBResult(list: List<DBExpense>) {
        var TransList : ArrayList <Transaction> = ArrayList<Transaction>()

        for ( i in list ) {
            i as Transaction
            TransList.add(i)
        }

        filterByTime(TransList, filterSheet.periodFilter)

        for (i in list) {
            i as Expense
            var total = list.size

            val frequenciesByCategory = list.groupingBy { i.category }.eachCount()

            for (key in frequenciesByCategory.keys) {
                var value = frequenciesByCategory.getValue(key) / total * 1.0f
                valuesList.add(PieEntry(value, key))
            }
        }

    }
}