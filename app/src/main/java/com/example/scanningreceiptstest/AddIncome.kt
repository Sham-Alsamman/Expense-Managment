package com.example.scanningreceiptstest

import android.app.DatePickerDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.activity_add_income.*
import kotlinx.android.synthetic.main.activity_add_manually.*
import java.util.*


class AddIncome : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_income)



       saveIncome.setOnClickListener {

           var name=nameIncome.text.toString()
           var catIncome=categoryIncome.text.toString()
           var amountIN=amountIncome.text.toString()

           var date=dateIncome.text.toString().split("-").toTypedArray()


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

           var dd=Date(yearInt-1900,monthInt-1,dayInt)

           Toast.makeText(this,"date: "+dd, Toast.LENGTH_LONG).show()

           var newIncome= Income(dd,amountIN.toDouble(),"","",catIncome,name);
       }

    }

    fun clickDataPicker(view: View) {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

            dateIncome.setText("$dayOfMonth-${monthOfYear + 1}-$year")

        }, year, month, day)
        dpd.show()
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

        //set max date to the current date:
        datePickerDialog.datePicker.maxDate = calendar.timeInMillis

        datePickerDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        datePickerDialog.show()
    }
}