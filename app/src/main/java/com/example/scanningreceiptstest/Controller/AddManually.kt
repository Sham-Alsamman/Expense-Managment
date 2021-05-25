package com.example.scanningreceiptstest.Controller

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import com.example.scanningreceiptstest.Model.Expense
import com.example.scanningreceiptstest.R
import com.example.scanningreceiptstest.database.CURRENT_USER
import kotlinx.android.synthetic.main.activity_add_manually.*
import java.util.*

class AddManually : NavDrawerActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_manually)

        val vendorFromScan = intent.getStringExtra("venName")
        val totalFromScan = intent.getDoubleExtra("Total", 0.0)

        if (vendorFromScan != null) {
            NameIn.setText(vendorFromScan)
            amountIn.setText(totalFromScan.toString())
        }

        outlinedTextField.editText?.doOnTextChanged { text, start, before, count ->
            outlinedTextField.error = null
        }

        outDate1.editText?.doOnTextChanged { text, start, before, count ->
            outDate1.error = null
        }

        outAmountManually.editText?.doOnTextChanged { text, start, before, count ->
            outAmountManually.error = null
        }

        outDate1.setEndIconOnClickListener {
            showDatePicker()
        }

        saveExpense.setOnClickListener {
            getExpenseDataAndSave()
        }

    }

    private fun getExpenseDataAndSave() {
        var name = ""
        var amountExpense = ""
        var date = arrayOf<String>()
        var dateExp: Date = Date()
        var dayInt: Int = 0
        var monthInt: Int = 0
        var yearInt: Int = 0
        var flag = true

        if (!NameIn.text.isNullOrEmpty()) {
            name = NameIn.text.toString()
            outlinedTextField.error = null
        } else {
            outlinedTextField.error = "Please enter the Name of Receipt"
            flag = false
        }
        if (!amountIn.text.isNullOrEmpty()) {
            amountExpense = amountIn.text.toString()
            outAmountManually.error = null
        } else {
            outAmountManually.error = "Please enter the amount of Receipt"
            flag = false
        }

        if (!dateIn.text.isNullOrEmpty()) {
            outDate1.error = null
            date = dateIn.text.toString().split("/").toTypedArray()
            try {
                dayInt = date[0].toInt()
                monthInt = date[1].toInt()
                yearInt = date[2].toInt()
            } catch (e: Exception) {
                outDate1.error = "Incorrect date"
                flag = false
            }
            dateExp = Date(yearInt - 1900, monthInt - 1, dayInt)

        } else {
            outDate1.error = "Please Enter The Date Of Receipt"
            flag = false
        }

        val catSelected: String = spinnerCat.selectedItem as String

        if (flag) {
            val newExpense =
                Expense(dateExp, amountExpense.toDouble(), catSelected, name)

            saveNewExpense(newExpense)
        }
    }

    private fun saveNewExpense(newExpense: Expense) {

        if (CURRENT_USER!!.addExpenseIfPossible(newExpense.amount)) {
            addExpenseToDB(newExpense)
        } else if (CURRENT_USER!!.canWithdrawFromSavings(newExpense.amount)) {
            AlertDialog.Builder(this)
                .setTitle("Withdraw from savings")
                .setMessage("Your current balance is not enough, Do you want to use your savings?")
                .setPositiveButton("Yes") { dialog, id ->
                    CURRENT_USER!!.withdrawFromSaving(newExpense.amount)
                    addExpenseToDB(newExpense)
                }
                .setNegativeButton("No") { dialog, id ->
                    dialog.cancel()
                }
                .show()
        } else
            Toast.makeText(this, "Your balance in not enough!", Toast.LENGTH_SHORT).show()
    }

    private fun addExpenseToDB(newExpense: Expense) {
        database.addNewExpense(CURRENT_USER!!.phoneNumber, newExpense.toDBExpense())
        database.updateUserInfo(CURRENT_USER!!.toDBPerson())
        Toast.makeText(this, "Expense added successfully", Toast.LENGTH_SHORT).show()

        val intent = Intent(this, Home::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
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

                outDate1.editText!!.setText(date)

            },
            currentYear,
            currentMonth,
            currentDay
        )

        datePickerDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        datePickerDialog.show()
    }
}


