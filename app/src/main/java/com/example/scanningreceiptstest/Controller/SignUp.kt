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

class SignUp : NavDrawerActivity () {


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
                Name.setEndIconDrawable(R.drawable.ic_baseline_error_24)
                Name.error = "Name Should Contain Only Characters and Numbers"
            }
            else if (text.isNullOrEmpty()) {
                Name.setEndIconDrawable(R.drawable.ic_baseline_error_24)
                Name.error = "Enter Your Name "
            }
            else {
                Name.setEndIconDrawable(0)
                Name.error = null
            }
        }
        PhoneSignUp.editText?.doOnTextChanged { text, start, before, count ->
            if (text.isNullOrEmpty()) {
                PhoneSignUp.setEndIconDrawable(R.drawable.ic_baseline_error_24)
                PhoneSignUp.error = "Enter Your Phone Number "
            }
            else if(!text.startsWith("+")){
                PhoneSignUp.setEndIconDrawable(R.drawable.ic_baseline_error_24)
                PhoneSignUp.error ="Enter Country code also along with the phone number"
            }
            else {
                PhoneSignUp.setEndIconDrawable(0)
                PhoneSignUp.error = null
            }
        }
        pass.editText?.doOnTextChanged { text, start, before, count ->
            val pattern = Pattern.compile(reg)
            val matcher: Matcher = pattern.matcher(text)
            if (matcher.find()) {
                pass.setEndIconActivated(false)
                // PasswordEt.setEndIconDrawable(R.drawable.ic_baseline_error_24)
                pass.error = "Password should contain only characters and numbers"
            } else if (text.isNullOrEmpty()) {
                // PasswordET.setEndIconDrawable(R.drawable.ic_baseline_error_24)
                pass.error = "Enter Password"
            }
            else if(text.length<8){
                pass.error = "Password must at least 8 characters"
            }
            else {
                //PasswordETSignUp.setEndIconDrawable(0)
                pass.error = null
            }
        }
        RePass.editText?.doOnTextChanged { text, start, before, count ->
            val match: String = pass.editText?.text.toString()
            val match2: String = RePass.editText?.text.toString()
            if(!match2!!.equals(match)){
                RePass.error="Password are not matching"
            }
            else if (text.isNullOrEmpty()) {
                // PasswordET.setEndIconDrawable(R.drawable.ic_baseline_error_24)
                RePass.error = "Re-type Password"
            }
            else {
                //PasswordETSignUp.setEndIconDrawable(0)
                RePass.error = null
            }
        }
    }

    fun SignInTextView(view: View) {
        val i = Intent(applicationContext, Login::class.java)
        startActivity(i)
    }

    /********************************/
    fun goToVerification(view: View) {
        /***check if all fields are correct..**/

        //open verification page:
        val intent = Intent(applicationContext, Verification::class.java)
        intent.putExtra(PHONE_NUMBER_EXTRA, PhoneETSignUp.editText?.text.toString())
        startActivity(intent)
    }

}

