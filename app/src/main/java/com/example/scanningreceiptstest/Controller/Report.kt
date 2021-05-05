package com.example.scanningreceiptstest.Controller

//import com.example.gp22.BottomSheet_Filter
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import com.example.scanningreceiptstest.R
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry

class Report : NavDrawerActivity() {

    private lateinit var pieChart : PieChart;
    private lateinit var buttonOpenDialog : Button;
    private var values = ArrayList<PieEntry>();



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report)
        onCreateDrawer()

//        val a = ArrayList<String>();
//            a.add("Last month");
//            a.add("Last 2 months");
//            a.add("Last 3 months");
//            a.add("last 4 months");
//            a.add("Last year");
//



        pieChart = findViewById(R.id.piechart);

        values.add(PieEntry(40f, "Jan"));
        values.add(PieEntry(60f, "Feb"));
        /*var bottomsheetFilter = BottomSheet_Filter(this)


        filter_btn.setOnClickListener {
            bottomsheetFilter.show(supportFragmentManager, "BottomSheetDialog")
        }*/
/*

btn!!.setOnClickListener {
                var d = "" //:String = savedDate.text.toString()
                var t = ""

                if(individual.isSelected)
                    t = "individual"
                else if(group.isSelected)
                    t = "group"

                //go to transactions list and bring the data in date d and group or individally and add the data to generate method

                bottomsheetFilter.dismiss()
                generate(values)
            }
        btn.setOnClickListener {
        }
    */

        generate(values)
    }

    public fun apply(d:String, t: String){
        generate(values)
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


}