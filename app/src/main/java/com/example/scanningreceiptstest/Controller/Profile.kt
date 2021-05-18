//package com.example.scanningreceiptstest
package com.example.scanningreceiptstest.Controller


//import android.app.Person
import android.content.Intent
import android.os.Bundle
import android.widget.EditText

import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import at.favre.lib.crypto.bcrypt.BCrypt

import com.example.scanningreceiptstest.R
import com.example.scanningreceiptstest.database.CURRENT_USER
import com.example.scanningreceiptstest.database.Database
import kotlinx.android.synthetic.main.profile_activity.*
import java.util.regex.Matcher
import java.util.regex.Pattern

class Profile : NavDrawerActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile_activity)
        onCreateDrawer()
        val reg: String = "\\p{Punct}"

       var userName=findViewById<EditText>(R.id.edit_text)
        userName.setText(CURRENT_USER!!.name)

        changePass.doOnTextChanged { text, start, before, count ->
                val pattern = Pattern.compile(reg)
                val matcher: Matcher = pattern.matcher(text)
                if (matcher.find()) {
                   // filledTextField4.setEndIconActivated(false)
                    //filledTextField4.setEndIconDrawable(R.drawable.ic_baseline_error_24)
                    changePass.error = "Password should contain only characters and numbers"
                } else if (text.isNullOrEmpty()) {
                  //  filledTextField4.setEndIconDrawable(R.drawable.ic_baseline_error_24)
                    changePass.error = "Enter Password"
                } else if (text.length < 8) {
                    changePass.error = "Password must at least 8 characters"
                } else {
                    //filledTextField4.setEndIconDrawable(0)
                    changePass.error = null
                }
            }
        ConfirmPass.doOnTextChanged { text, start, before, count ->
            val match: String = changePass.text.toString()
            val match2: String = ConfirmPass.text.toString()
            if (!match2!!.equals(match)) {
                ConfirmPass.error = "Password are not matching"
            } else if (text.isNullOrEmpty()) {
               // filledTextField5.setEndIconDrawable(R.drawable.ic_baseline_error_24)
                ConfirmPass.error = "Re-type Password"
            } else {
                //PasswordETSignUp.setEndIconDrawable(0)
                ConfirmPass.error = null
            }
        }

        SaveText.setOnClickListener {


          saveInfo()
/*
            val listTransaction = mutableListOf<Transaction>()
            val m = com.example.scanningreceiptstest.Model.Person(
                "+962791558798",
                "Malak",
                "4",
                1000.500,
                1000.00,
                100.00,
                100.00,
                listTransaction
            )
            Database.updateUserInfo("+962791558798",m.toDBPerson())
 */


        }
    }

    private fun saveInfo()
    {

        if ( changePass.text!!.isEmpty())
            Toast.makeText(applicationContext, "Please New Password field ", Toast.LENGTH_LONG).show()
        else if ( ConfirmPass.text!!.isEmpty())
            Toast.makeText(applicationContext, "Please Confirm Password field ", Toast.LENGTH_LONG).show()
        else if (changePass.error != null )
            Toast.makeText(applicationContext, changePass.error, Toast.LENGTH_LONG).show()
        else if (ConfirmPass.error != null)
            Toast.makeText(applicationContext, ConfirmPass.error, Toast.LENGTH_LONG).show()
        else
        {
            var newUserName=findViewById<EditText>(R.id.edit_text).text.toString()
            var newPass=findViewById<EditText>(R.id.changePass).text.toString()
            //  var hashPass= BCrypt.withDefaults().hashToString(12,newPass.toCharArray())

            CURRENT_USER!!.name=newUserName
            CURRENT_USER!!.password=BCrypt.withDefaults().hashToString(12,newPass.toCharArray())
            Database.updateUserInfo(CURRENT_USER!!.toDBPerson())

            Toast.makeText(applicationContext, "Update Successful", Toast.LENGTH_LONG).show()

            var newIntent= Intent(this,Home::class.java)
            startActivity(newIntent)
        }

    }
}