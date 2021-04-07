//package com.example.scanningreceiptstest
package com.example.scanningreceiptstest.Controller


import android.app.DatePickerDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.scanningreceiptstest.Model.Expense
import com.example.scanningreceiptstest.Model.Income
import com.example.scanningreceiptstest.R
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.activity_add_income.*
import kotlinx.android.synthetic.main.activity_add_income.outDate
import kotlinx.android.synthetic.main.activity_add_manually.*
import java.util.*

class AddManually : NavDrawerActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_manually)

        outDate1.setEndIconOnClickListener(View.OnClickListener {
            showDatePicker()
        })

        saveExpense.setOnClickListener {
            var name= NameIn.text.toString()
            var catExpense=catIn.text.toString()
            var amountExpense=amountIn.text.toString()
            var date=dateIn.text.toString().split("/").toTypedArray()

            /*  Toast.makeText(this,"year: "+date[2].toString(),Toast.LENGTH_SHORT).show()
              Toast.makeText(this,"month: "+date[1].toString(),Toast.LENGTH_SHORT).show()
              Toast.makeText(this,"day: "+date[0].toString(),Toast.LENGTH_SHORT).show()*/

            // for(item in date)
            //Toast.makeText(this,"date : "+item,Toast.LENGTH_SHORT).show()
            var dayInt= date[0].toInt()
            var monthInt= date[1].toInt()
            var yearInt= date[2].toInt()

            /* Toast.makeText(this,"year: "+yearInt,Toast.LENGTH_SHORT).show()
             Toast.makeText(this,"month: "+monthInt,Toast.LENGTH_SHORT).show()
             Toast.makeText(this,"day: "+dayInt,Toast.LENGTH_SHORT).show()*/

            var dateExp=Date(yearInt-1900,monthInt-1,dayInt)
            Toast.makeText(this,"date: "+dateExp, Toast.LENGTH_LONG).show()

            var newExpense= Expense(dateExp,amountExpense.toDouble(),"",catExpense,name);

        }
    }


    private fun showDatePicker() {
        //get current date:
        val calendar = Calendar.getInstance()
        val currentDay = calendar.get(Calendar.DAY_OF_MONTH)
        val currentMonth = calendar.get(Calendar.MONTH)
        val currentYear = calendar.get(Calendar.YEAR)

        //create date picker dialog with the current date:
        val datePickerDialog = DatePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, DatePickerDialog.OnDateSetListener{ view, year, monthOfYear, dayOfMonth ->

            // Display selected date in edit text:
            val date = String.format("$dayOfMonth/" + (monthOfYear + 1) + "/$year")

            outDate1.editText!!.setText(date)

        }, currentYear, currentMonth, currentDay)

        datePickerDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        datePickerDialog.show()
    }
}


