package com.example.scanningreceiptstest.Controller


import android.content.Intent
import android.os.Bundle
import android.widget.EditText

import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import at.favre.lib.crypto.bcrypt.BCrypt

import com.example.scanningreceiptstest.R
import com.example.scanningreceiptstest.database.CURRENT_USER
import kotlinx.android.synthetic.main.profile_activity.*
import java.util.regex.Matcher
import java.util.regex.Pattern

class Profile : NavDrawerActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile_activity)
        onCreateDrawer()
        val reg: String = "\\p{Punct}"

        var userName = findViewById<EditText>(R.id.nameProfile)
        userName.setText(CURRENT_USER!!.name)

        Profile.editText?.doOnTextChanged { text, start, before, count ->
            val pattern = Pattern.compile(reg)
            val matcher: Matcher = pattern.matcher(text!!)
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
                filledTextField4.error = "Password should contain only characters and numbers"
            } else if (text.isNullOrEmpty()) {
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
            if (match2 != match) {
                confirm.error = "Password are not matching"
            } else if (text.isNullOrEmpty()) {
                confirm.error = "Re-type Password"
            } else {
                confirm.error = null
            }
        }

        SaveText.setOnClickListener {
            saveInfo()
        }
    }

    private fun saveInfo() {

        if (changePass.text!!.isEmpty())
            Toast.makeText(applicationContext, "Please New Password field ", Toast.LENGTH_LONG)
                .show()
        else if (ConfirmPass.text!!.isEmpty())
            Toast.makeText(applicationContext, "Please Confirm Password field ", Toast.LENGTH_LONG)
                .show()
        else if (changePass.error != null)
            Toast.makeText(applicationContext, changePass.error, Toast.LENGTH_LONG).show()
        else if (ConfirmPass.error != null)
            Toast.makeText(applicationContext, ConfirmPass.error, Toast.LENGTH_LONG).show()
        else {
            var newUserName = findViewById<EditText>(R.id.invitePhoneNumInnerET).text.toString()
            var newPass = findViewById<EditText>(R.id.changePass).text.toString()

            CURRENT_USER!!.name = newUserName
            CURRENT_USER!!.password = BCrypt.withDefaults().hashToString(12, newPass.toCharArray())
            database.updateUserInfo(CURRENT_USER!!.toDBPerson())

            Toast.makeText(applicationContext, "Update Successful", Toast.LENGTH_LONG).show()

            var newIntent = Intent(this, Home::class.java)
            startActivity(newIntent)
        }

    }
}