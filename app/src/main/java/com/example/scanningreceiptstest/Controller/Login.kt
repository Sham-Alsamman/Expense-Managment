package com.example.scanningreceiptstest.Controller

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.widget.doOnTextChanged
import com.example.scanningreceiptstest.R
import com.example.scanningreceiptstest.SalaryAlarmReceiver
import com.example.scanningreceiptstest.SaveSharedPreference
import com.example.scanningreceiptstest.database.*
import com.example.scanningreceiptstest.setSalaryAlarmIfNotExist
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.activity_login.*
import java.util.regex.Matcher
import java.util.regex.Pattern

var alarmReceiver: SalaryAlarmReceiver? = null

class Login : NavDrawerActivity() {

    private val reg: String = "\\p{Punct}"

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val PhoneNum = findViewById<TextInputLayout>(R.id.phoneNumET)
        val Password = findViewById<TextInputLayout>(R.id.PasswordEt)

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
            val matcher: Matcher = pattern.matcher(text!!)
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

    private fun DbResultPerson(person: DBPerson ) {
        CURRENT_USER = person.toPerson()
        database.getExpenseGroup(CURRENT_USER!!.groupId, ::DbResultExpenseGroup)

        if(CURRENT_USER!= null) {
            val i = Intent(applicationContext, Home::class.java)
            startActivity(i)
            finish()
        }
    }

    private fun DbResultExpenseGroup(ExpenseGroup: DBExpenseGroup) {
        CURRENT_GROUP = ExpenseGroup.toExpenseGroup()
        SaveSharedPreference.saveUserData(this)
        /***********/
        setSalaryAlarmIfNotExist(this)
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
                checkIfPasswordExist(phoneNumET.editText?.text.toString(),PasswordEt.editText?.text.toString())

        }
    }
    private fun checkIfPasswordExist(phoneNum: String, Pass :String) {
        //check if the phone number and password exists in the database or not
        database.checkPassword(phoneNum,Pass,::onDBResult)
    }

    private fun onDBResult(exist: Boolean){
        if(exist) {
            database.getUser(phoneNumET.editText?.text!!.toString(), ::DbResultPerson)
            //Database.getExpenseGroup(CURRENT_USER!!.groupId, ::DbResultExpenseGroup)

        }else {
            Toast.makeText(
                applicationContext,
                "Incorrect Password or Phone Number ",
                Toast.LENGTH_LONG
            ).show()
        }
    }

}