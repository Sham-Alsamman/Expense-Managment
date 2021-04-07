package com.example.scanningreceiptstest.Controller

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import com.example.scanningreceiptstest.R
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.phoneNumET
import kotlinx.android.synthetic.main.activity_sign_up.*
import java.util.regex.Matcher
import java.util.regex.Pattern

class SignUp : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        val reg: String = "\\p{Punct}"

        val Name = findViewById<TextInputLayout>(R.id.NameET)
        val PhoneSignUp = findViewById<TextInputLayout>(R.id.PhoneETSignUp)
        val pass = findViewById<TextInputLayout>(R.id.PasswordETSignUp)
        val RePass = findViewById<TextInputLayout>(R.id.RepasswordET)

        Name.editText?.doOnTextChanged { text, start, before, count ->
            val pattern = Pattern.compile(reg)
            val matcher: Matcher = pattern.matcher(text)
            if (matcher.find()) {
                NameET.setEndIconDrawable(R.drawable.ic_baseline_error_24)
                NameET.error = "Name Should Contain Only Characters and Numbers"
            }
            else if (text.isNullOrEmpty()) {
                NameET.setEndIconDrawable(R.drawable.ic_baseline_error_24)
                NameET.error = "Enter Your Name "
            }
            else {
                NameET.setEndIconDrawable(0)
                NameET.error = null
            }
        }
        PhoneSignUp.editText?.doOnTextChanged { text, start, before, count ->
            if (text.isNullOrEmpty()) {
                PhoneETSignUp.setEndIconDrawable(R.drawable.ic_baseline_error_24)
                PhoneETSignUp.error = "Enter Your Phone Number "
            }
            else if(!text.startsWith("079") && !text.startsWith("078") && !text.startsWith("077")){
                PhoneETSignUp.setEndIconDrawable(R.drawable.ic_baseline_error_24)
                PhoneETSignUp.error ="Should Start with 077 or 078 or 079 "
            }
            else if(text.length<PhoneETSignUp.counterMaxLength) {
                PhoneETSignUp.setEndIconDrawable(R.drawable.ic_baseline_error_24)
                PhoneETSignUp.error = "Phone number must be 10 numbers long"
            }
            else if(text.length>PhoneETSignUp.counterMaxLength) {
                PhoneETSignUp.setEndIconDrawable(R.drawable.ic_baseline_error_24)
                PhoneETSignUp.error = "Error"
            }
            else {
                PhoneETSignUp.setEndIconDrawable(0)
                PhoneETSignUp.error = null
            }
        }
        pass.editText?.doOnTextChanged { text, start, before, count ->
            val pattern = Pattern.compile(reg)
            val matcher: Matcher = pattern.matcher(text)
            if (matcher.find()) {
                PasswordETSignUp.setEndIconActivated(false)
                // PasswordEt.setEndIconDrawable(R.drawable.ic_baseline_error_24)
                PasswordETSignUp.error = "Password should contain only characters and numbers"
            } else if (text.isNullOrEmpty()) {
                // PasswordET.setEndIconDrawable(R.drawable.ic_baseline_error_24)
                PasswordETSignUp.error = "Enter Password"
            }
            else if(text.length<8){
                PasswordETSignUp.error = "Password must at least 8 characters"
            }
            else {
                //PasswordETSignUp.setEndIconDrawable(0)
                PasswordETSignUp.error = null
            }
        }
        RePass.editText?.doOnTextChanged { text, start, before, count ->
            val match: String = pass.editText?.text.toString()
            val match2: String = RePass.editText?.text.toString()
            if(!match2!!.equals(match)){
                RepasswordET.error="Password are not matching"
            }
            else if (text.isNullOrEmpty()) {
                // PasswordET.setEndIconDrawable(R.drawable.ic_baseline_error_24)
                RepasswordET.error = "Re-type Password"
            }
            else {
                //PasswordETSignUp.setEndIconDrawable(0)
                RepasswordET.error = null
            }
        }
    }

    fun SignInTextView(view: View) {
        val i = Intent(applicationContext, Login::class.java)
        startActivity(i)
    }

}

