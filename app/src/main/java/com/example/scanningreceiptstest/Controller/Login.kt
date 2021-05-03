package com.example.scanningreceiptstest.Controller

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.widget.doOnTextChanged
import com.example.scanningreceiptstest.Model.ExpenseGroup
import com.example.scanningreceiptstest.Model.PeriodTransactionFilter
import com.example.scanningreceiptstest.Model.Person
import com.example.scanningreceiptstest.Model.Transaction
import com.example.scanningreceiptstest.R
import com.example.scanningreceiptstest.database.CURRENT_USER
import com.example.scanningreceiptstest.database.DBPerson
import com.example.scanningreceiptstest.database.Database
import com.example.scanningreceiptstest.database.toPerson
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.activity_login.*
import java.text.SimpleDateFormat
import java.time.YearMonth
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern

class Login : NavDrawerActivity() {


    val reg: String = "\\p{Punct}"
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val PhoneNum = findViewById<TextInputLayout>(R.id.phoneNumET)
        val Password = findViewById<TextInputLayout>(R.id.PasswordEt)

/*
        var m =  SimpleDateFormat("MM/dd/yyyy").parse("2/5/2021")
        var m2 =  SimpleDateFormat("MM/dd/yyyy").parse("2/3/2021")
        var m3 =  SimpleDateFormat("MM/dd/yyyy").parse("2/2/2021")



        val list: List<Transaction> = emptyList()
        val list2: MutableList<String> = mutableListOf()
        var pp :Person = Person("maalk", "+962791558798")
        */
         /*
        pp.transactions[0].date=m;
        pp.transactions[0].amount=900.00;
        pp.transactions[1].date=m2;
        pp.transactions[1].amount=600.00;
        pp.transactions[2].date=m2;
        pp.transactions[2].amount=345.00;


          */
          /*
        val now = Calendar.getInstance()
        val thisMonth = now.add(Calendar.MONTH,1)
        Toast.makeText(this, "this  month  " + now.getTime().month, Toast.LENGTH_LONG).show()
        val twoMonthsAgo = now.add(Calendar.MONTH,-2)
        Toast.makeText(this, "2  month  " + now.getTime().month, Toast.LENGTH_LONG).show()
        val threeMonth = now.add(Calendar.MONTH,-1)
        Toast.makeText(this, "3  month  " + now.getTime().month, Toast.LENGTH_LONG).show()
        val fourMonth = now.add(Calendar.MONTH,-1)
        Toast.makeText(this, "4  month  " + now.getTime().month, Toast.LENGTH_LONG).show()
        now.add(Calendar.YEAR, -1)
        Toast.makeText(this, "year is   " + now.get(Calendar.YEAR), Toast.LENGTH_LONG).show()


           */
        // val monthYearFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("MMMM yyyy")
        /*
        var intThisMonth =thisMonth.format(monthYearFormatter).toInt()
        var intTwoMonth =twoMonthsAgo.format(monthYearFormatter).toInt()
        var intThreeMonth =threeMonth.format(monthYearFormatter).toInt()
        var intFourMonth =fourMonth.format(monthYearFormatter).toInt()

         */

      // var dd :ExpenseGroup =ExpenseGroup("1",list2)
      //  var r = ExpenseGroup.filterTransactions(list, PeriodTransactionFilter.Last2Months)
        // Toast.makeText(this, "list is " + r.toString(), Toast.LENGTH_LONG).show()

        PhoneNum.editText?.doOnTextChanged { text, start, before, count ->
            if (text!!.isEmpty()) {
                PhoneNum.setEndIconDrawable(R.drawable.ic_baseline_error_24)
                PhoneNum.error = "Enter Your Phone Number "
            } else if (!(text.startsWith("+"))) {
                PhoneNum.setEndIconDrawable(R.drawable.ic_baseline_error_24)
                PhoneNum.error = "Enter Country code also along with the phone number"
            } else {
                PhoneNum.setEndIconDrawable(0)
                PhoneNum.error = null
            }
        }

        Password.editText?.doOnTextChanged { text, start, before, count ->
            val pattern = Pattern.compile(reg)
            val matcher: Matcher = pattern.matcher(text)
            if (matcher.find()) {
                Password.setEndIconActivated(false)
                // PasswordEt.setEndIconDrawable(R.drawable.ic_baseline_error_24)
                Password.error = "Password Should Contain Only Characters and Numbers"
            } else if (text.isNullOrEmpty()) {
                //  PasswordEt.setEndIconDrawable(R.drawable.ic_baseline_error_24)
                Password.error = "Enter Password"
            } else {
                //PasswordEt.setEndIconDrawable(0)
                Password.error = null

            }
        }


    }
    fun DbResultPerson(person: DBPerson){

        CURRENT_USER=person.toPerson()
    }


    fun SignUpTextView(view: View) {
        val i = Intent(applicationContext, SignUp::class.java)
        startActivity(i)
    }

    fun Login(view: View) {
        // val i = Intent(applicationContext, ::class.java)
        //startActivity(i)
        if (phoneNumET.editText?.text!!.isEmpty() && PasswordEt.editText?.text!!.isEmpty()) {
            Toast.makeText(
                applicationContext,
                "Please enter phone number and password ",
                Toast.LENGTH_LONG
            ).show()
        } else if (phoneNumET.editText?.text!!.isEmpty()) {
            Toast.makeText(applicationContext, "Please enter phone number ", Toast.LENGTH_LONG)
                .show()
        } else if (PasswordEt.editText?.text!!.isEmpty()) {
            Toast.makeText(applicationContext, "Please enter password ", Toast.LENGTH_LONG).show()
        } else if (phoneNumET.error != null) {
            Toast.makeText(applicationContext, phoneNumET.error, Toast.LENGTH_LONG).show()

        } else if (PasswordEt.error != null) {
            Toast.makeText(applicationContext, PasswordEt.error, Toast.LENGTH_LONG).show()
        } else {
            Database.getUser(phoneNumET.editText?.text!!.toString(), ::DbResultPerson)
            // should go to the home activity
        }
    }

}