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
import com.example.scanningreceiptstest.Model.Transaction
import kotlinx.android.synthetic.main.bottomsheet_filter.*

class Report : NavDrawerActivity(), IFilterSheet {


    companion object {

        lateinit var pieChart: PieChart;
        var valuesList = ArrayList<PieEntry>();

        fun get_Filtered_transactions(list: List<Transaction>) {
            for (i in list) {
                i as Expense
                val frequenciesByCategory = list.groupingBy { i.category }.eachCount()
                for (key in frequenciesByCategory.keys) {
                    var total = list.size

                    var value = frequenciesByCategory.getValue(key) / total * 1.0f
                    valuesList.add(PieEntry(value, key))
                }
            }
        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report)
        onCreateDrawer()


        pieChart = findViewById(R.id.piechart);

        valuesList.add(PieEntry(40f, "Jan"));
        valuesList.add(PieEntry(60f, "Feb"));


        generate(valuesList)
    }

    public fun generate(values : ArrayList<PieEntry>){

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
        //
    }
}