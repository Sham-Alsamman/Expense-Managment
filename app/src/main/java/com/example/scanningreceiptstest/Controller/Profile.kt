//package com.example.scanningreceiptstest
package com.example.scanningreceiptstest.Controller


//import android.app.Person
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText

import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import at.favre.lib.crypto.bcrypt.BCrypt
import com.example.scanningreceiptstest.Model.Transaction

import com.example.scanningreceiptstest.R
import com.example.scanningreceiptstest.database.CURRENT_USER
import com.example.scanningreceiptstest.database.Database
import com.example.scanningreceiptstest.Model.Person
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.profile_activity.*
import java.util.regex.Matcher
import java.util.regex.Pattern

class Profile : NavDrawerActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile_activity)
        onCreateDrawer()
        val reg: String = "\\p{Punct}"

       var userName=findViewById<EditText>(R.id.nameProfile)
        userName.setText(CURRENT_USER!!.name)

        Profile.editText?.doOnTextChanged { text, start, before, count ->
            val pattern = Pattern.compile(reg)
            val matcher: Matcher = pattern.matcher(text)
            if (matcher.find()) {
                Profile.setEndIconDrawable(R.drawable.ic_baseline_error_24)
                Profile.error = "Name Should Contain Only Characters and Numbers"
            } else if (text.isNullOrEmpty()) {
                Profile.setEndIconDrawable(R.drawable.ic_baseline_error_24)
                Profile.error = "Enter Your Name "
            } else {
                Profile.setEndIconDrawable(0)
                Profile.error = null
            }
        }


        filledTextField4.editText?.doOnTextChanged { text, start, before, count ->
                val pattern = Pattern.compile(reg)
                val matcher: Matcher = pattern.matcher(text)
                if (matcher.find()) {
                    //filledTextField4.setEndIconActivated(false)
                    //filledTextField4.setEndIconDrawable(R.drawable.ic_baseline_error_24)
                    filledTextField4.error = "Password should contain only characters and numbers"
                } else if (text.isNullOrEmpty()) {
                   // filledTextField4.setEndIconDrawable(R.drawable.ic_baseline_error_24)
                    filledTextField4.error = "Enter Password"
                } else if (text.length < 8) {
                    filledTextField4.error = "Password must at least 8 characters"
                } else {
                    filledTextField4.setEndIconDrawable(0)
                    filledTextField4.error = null
                }
            }
        confirm.editText?.doOnTextChanged { text, start, before, count ->
            val match: String = changePass.text.toString()
            val match2: String = ConfirmPass.text.toString()
            if (!match2!!.equals(match)) {
                confirm.error = "Password are not matching"
            } else if (text.isNullOrEmpty()) {
               // filledTextField5.setEndIconDrawable(R.drawable.ic_baseline_error_24)
                confirm.error = "Re-type Password"
            } else {
                //PasswordETSignUp.setEndIconDrawable(0)
                confirm.error = null
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

            CURRENT_USER!!.name=newUserName
            CURRENT_USER!!.password=BCrypt.withDefaults().hashToString(12,newPass.toCharArray())
            Database.updateUserInfo(CURRENT_USER!!.toDBPerson())

            Toast.makeText(applicationContext, "Update Successful", Toast.LENGTH_LONG).show()

            var newIntent= Intent(this,Home::class.java)
            startActivity(newIntent)
        }

    }
}