//package com.example.scanningreceiptstest
package com.example.scanningreceiptstest.Controller

import android.app.DatePickerDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import com.example.scanningreceiptstest.Model.Expense
import kotlinx.android.synthetic.main.activity_add_income.*
import com.example.scanningreceiptstest.Model.Income
import com.example.scanningreceiptstest.R
import com.example.scanningreceiptstest.database.Database
import kotlinx.android.synthetic.main.activity_add_manually.*
import java.text.SimpleDateFormat
import java.util.*


class AddIncome : NavDrawerActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_income)
        var name=""
        var amountIN=""
        var date=arrayOf<String>()
        var DateIncome=Date()
        var dayInt= 0
        var monthInt= 0
        var yearInt= 0
        var flag=true

        outName.editText?.doOnTextChanged { text, start, before, count ->
            outName.error=null
        }

        outDate.editText?.doOnTextChanged { text, start, before, count ->
            outDate.error=null
        }

        outAmount.editText?.doOnTextChanged { text, start, before, count ->
            outAmount.error=null
        }

        outDate.setEndIconOnClickListener {
            showDatePicker()
        }

        saveIncome.setOnClickListener {
            if (!nameIncome.text.isNullOrEmpty()) {
                name= nameIncome.text.toString()
                outName.error=null
            }
            else
            {
                nameIncome.error="Please enter the Name of Receipt"
                flag=false
            }

            if (!amountIncome.text.isNullOrEmpty()) {
                amountIN=amountIncome.text.toString()
                outAmount.error=null
            }
            else
            {
                outAmount.error="Please enter the amount of Receipt"
                flag=false
            }

            if (!dateIncome.text.isNullOrEmpty()) {
                outDate.error=null
                date = dateIncome.text.toString().split("/").toTypedArray()
                dayInt = date[0].toInt()
                monthInt = date[1].toInt()
                yearInt = date[2].toInt()

                DateIncome=Date(yearInt-1900,monthInt-1,dayInt)
                Toast.makeText(this,"date: "+DateIncome, Toast.LENGTH_LONG).show()


            }
            else{
                outDate.error="Please Enter The Date Of Receipt"
                flag=false
            }

            if (flag) {
                var newIncome = Income(DateIncome, amountIN.toDouble(), name)
            }
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