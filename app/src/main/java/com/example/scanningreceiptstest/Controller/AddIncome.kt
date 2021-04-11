//package com.example.scanningreceiptstest
package com.example.scanningreceiptstest.Controller

import android.app.DatePickerDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add_income.*
import com.example.scanningreceiptstest.Model.Income
import com.example.scanningreceiptstest.R
import java.util.*


class AddIncome : NavDrawerActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_income)

        outDate.setEndIconOnClickListener {
            showDatePicker()
        }

        saveIncome.setOnClickListener {

            var name=nameIncome.text.toString()
            var catIncome=categoryIncome.text.toString()
            var amountIN=amountIncome.text.toString()
            var noteIn:String?=NotesIncome.text.toString()

            var date=dateIncome.text.toString().split("/").toTypedArray()


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

            var dateIncome=Date(yearInt-1900,monthInt-1,dayInt)

            Toast.makeText(this,"date: "+dateIncome, Toast.LENGTH_LONG).show()
            Toast.makeText(this,"amount: "+amountIN, Toast.LENGTH_LONG).show()
            Toast.makeText(this,"noteIn: "+noteIn, Toast.LENGTH_LONG).show()
            Toast.makeText(this,"catIncome: "+catIncome, Toast.LENGTH_LONG).show()
            Toast.makeText(this,"name: "+name, Toast.LENGTH_LONG).show()

            var newIncome= Income(dateIncome,amountIN.toDouble(),noteIn,catIncome,name)
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

            outDate.editText!!.setText(date)

        }, currentYear, currentMonth, currentDay)

        //set max date to the current date:
        // datePickerDialog.datePicker.maxDate = calendar.timeInMillis

        datePickerDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        datePickerDialog.show()
    }
}