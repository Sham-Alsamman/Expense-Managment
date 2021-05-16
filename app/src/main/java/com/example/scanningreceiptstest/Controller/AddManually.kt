//package com.example.scanningreceiptstest
package com.example.scanningreceiptstest.Controller


import android.app.DatePickerDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.core.widget.doOnTextChanged
import com.example.scanningreceiptstest.Model.Expense
import com.example.scanningreceiptstest.Model.recEnum
import com.example.scanningreceiptstest.R
import com.example.scanningreceiptstest.database.CURRENT_USER
import com.example.scanningreceiptstest.database.Database
import kotlinx.android.synthetic.main.activity_add_manually.*
import java.text.SimpleDateFormat
import java.util.*


class AddManually : NavDrawerActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_manually)

        val vendorFromScan= intent.getStringExtra("venName").toString()
        val totalFromScan= intent.getDoubleExtra("Total",0.0).toString()

        NameIn.setText(vendorFromScan)
        amountIn.setText(totalFromScan)

        var recSelected: recEnum
        var catSelected: String
        val categoryItems = resources.getStringArray(R.array.Category)

        val spinnerRecurrent = findViewById<Spinner>(R.id.spinner)
        if (spinnerRecurrent != null) {
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_list_item_1, recEnum.values()
            )
            spinnerRecurrent.adapter = adapter


            spinnerRecurrent.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?, position: Int, id: Long
                ) {
                    //Toast.makeText(this@AddManually, getString(R.string.selected_item) + " " +
                    //       "" +spinner.selectedItem.toString(), Toast.LENGTH_SHORT
                    // ).show()
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    spinner.setSelection(adapter.getPosition(recEnum.valueOf("None")));
                }
            }


            val spinnerCategory = findViewById<Spinner>(R.id.spinnerCat)
            if (spinnerCategory != null) {
                val adapter2 = ArrayAdapter(
                    this,
                    android.R.layout.simple_spinner_item, categoryItems
                )
                spinnerCategory.adapter = adapter2


                spinnerCategory.onItemSelectedListener = object :
                    AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>,
                        view: View, position: Int, id: Long
                    ) {
                        //Toast.makeText(this@AddManually, getString(R.string.selected_item) + " " +
                        //       "" +spinner.selectedItem.toString(), Toast.LENGTH_SHORT
                        // ).show()
                    }

                    override fun onNothingSelected(parent: AdapterView<*>) {
                        spinnerCategory.setSelection(0)
                    }
                }
            }


            var name = ""
            var amountExpense = ""
            var date = arrayOf<String>()
            var dateExp: Date = Date()
            var dayInt: Int = 0
            var monthInt: Int = 0
            var yearInt: Int = 0
            var flag = true


            outlinedTextField.editText?.doOnTextChanged { text, start, before, count ->
                outlinedTextField.error = null
            }

            outDate1.editText?.doOnTextChanged { text, start, before, count ->
                outDate1.error = null
            }

            outAmountManually.editText?.doOnTextChanged { text, start, before, count ->
                outAmountManually.error = null
            }

            outDate1.setEndIconOnClickListener(View.OnClickListener {
                showDatePicker()
            })

            saveExpense.setOnClickListener {
                if (!NameIn.text.isNullOrEmpty()) {
                    name = NameIn.text.toString()
                    outlinedTextField.error = null
                } else {
                    outlinedTextField.error = "Please enter the Name of Receipt"
                    flag = false
                }
                /*  if (!catIn.text.isNullOrEmpty()) {
                      catExpense = catIn.text.toString()
                      outCat2.error = null
                  } else {
                      outCat2.error = "Please enter the Category of Receipt"
                      flag = false
                  }*/

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
                    dayInt = date[0].toInt()
                    monthInt = date[1].toInt()
                    yearInt = date[2].toInt()

                    dateExp = Date(yearInt - 1900, monthInt - 1, dayInt)
                   // Toast.makeText(this, "date: " + dateExp, Toast.LENGTH_LONG).show()
                    /*
                    val dateFormat = SimpleDateFormat("dd-MM-yyyy")
                    val date: Date = dateFormat.parse("2020-1-1")
                    val m = Expense(date, 200.00, "Clothes", "shop", recEnum.Daily)
                    Database.addNewExpense("+962791558798",m.toDBExpense())
                    */
                } else {
                    outDate1.error = "Please Enter The Date Of Receipt"
                    flag = false
                }

                recSelected = spinner.selectedItem as recEnum
                catSelected = spinnerCat.selectedItem as String

                /* Toast.makeText(this@AddManually, recSelected.toString(), Toast.LENGTH_SHORT).show()
                 Toast.makeText(this@AddManually,catSelected, Toast.LENGTH_SHORT).show()*/

                /*  Toast.makeText(this,"year: "+date[2].toString(),Toast.LENGTH_SHORT).show()
                  Toast.makeText(this,"month: "+date[1].toString(),Toast.LENGTH_SHORT).show()
                  Toast.makeText(this,"day: "+date[0].toString(),Toast.LENGTH_SHORT).show()*/

                // for(item in date)
                //Toast.makeText(this,"date : "+item,Toast.LENGTH_SHORT).show()

                /* Toast.makeText(this,"year: "+yearInt,Toast.LENGTH_SHORT).show()
                 Toast.makeText(this,"month: "+monthInt,Toast.LENGTH_SHORT).show()
                 Toast.makeText(this,"day: "+dayInt,Toast.LENGTH_SHORT).show()*/

                if (flag) {
                    var newExpense =
                        Expense(dateExp, amountExpense.toDouble(), catSelected, name, recSelected)

                    Database.addNewExpense(CURRENT_USER!!.phoneNumber, newExpense.toDBExpense())
                }

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


