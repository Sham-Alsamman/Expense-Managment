package com.example.scanningreceiptstest

import android.app.DatePickerDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.scanningreceiptstest.R
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.activity_add_income.*
import kotlinx.android.synthetic.main.activity_add_income.outDate
import kotlinx.android.synthetic.main.activity_add_manually.*
import java.util.*

class AddManually : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_manually)

        outDate1.setEndIconOnClickListener(View.OnClickListener {
            showDatePicker()
        })

       saveExpense.setOnClickListener {


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


