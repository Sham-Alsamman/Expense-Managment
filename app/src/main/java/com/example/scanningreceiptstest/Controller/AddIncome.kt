package com.example.scanningreceiptstest.Controller

import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import com.example.scanningreceiptstest.Model.Income
import com.example.scanningreceiptstest.R
import com.example.scanningreceiptstest.database.CURRENT_USER
import kotlinx.android.synthetic.main.activity_add_income.*
import java.util.*


class AddIncome : NavDrawerActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_income)

        outName.editText?.doOnTextChanged { text, start, before, count ->
            outName.error = null
        }

        outDate.editText?.doOnTextChanged { text, start, before, count ->
            outDate.error = null
        }

        outAmount.editText?.doOnTextChanged { text, start, before, count ->
            outAmount.error = null
        }

        outDate.setEndIconOnClickListener {
            showDatePicker()
        }

        saveIncome.setOnClickListener {
            addAdditionalIncome();
        }
    }

    private fun addAdditionalIncome(){
        var name = ""
        var amountIN = ""
        var date = arrayOf<String>()
        var DateIncome = Date()
        var dayInt = 0
        var monthInt = 0
        var yearInt = 0
        var flag = true

        if (!nameIncome.text.isNullOrEmpty()) {
            name = nameIncome.text.toString()
            outName.error = null
        } else {
            nameIncome.error = "Please enter the Name of the Income"
            flag = false
        }

        if (!amountIncome.text.isNullOrEmpty()) {
            amountIN = amountIncome.text.toString()
            outAmount.error = null
        } else {
            outAmount.error = "Please enter the amount of the Income"
            flag = false
        }

        if (!dateIncome.text.isNullOrEmpty()) {
            outDate.error = null
            date = dateIncome.text.toString().split("/").toTypedArray()
            try {
                dayInt = date[0].toInt()
                monthInt = date[1].toInt()
                yearInt = date[2].toInt()
            }catch (e: Exception){
                outDate.error = "Incorrect date"
                flag = false
            }
            DateIncome = Date(yearInt - 1900, monthInt - 1, dayInt)
        } else {
            outDate.error = "Please Enter The Date of the Income"
            flag = false
        }

        if (flag) {
            val newIncome = Income(DateIncome, amountIN.toDouble(), name)
            CURRENT_USER!!.addIncome(newIncome.amount)
            database.addNewIncome(CURRENT_USER!!.phoneNumber, newIncome.toDBIncome())
            database.updateUserInfo(CURRENT_USER!!.toDBPerson())
            Toast.makeText(this, "Income added successfully", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, Home::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }
    }

    private fun showDatePicker() {
        //get current date:
        val calendar = Calendar.getInstance()
        val currentDay = calendar.get(Calendar.DAY_OF_MONTH)
        val currentMonth = calendar.get(Calendar.MONTH)
        val currentYear = calendar.get(Calendar.YEAR)

        //create date picker dialog with the current date:
        val datePickerDialog = DatePickerDialog(
            this,
            android.R.style.Theme_Holo_Light_Dialog_MinWidth,
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

                // Display selected date in edit text:
                val date = String.format("$dayOfMonth/" + (monthOfYear + 1) + "/$year")

                outDate.editText!!.setText(date)

            },
            currentYear,
            currentMonth,
            currentDay
        )

        datePickerDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        datePickerDialog.show()
    }
}