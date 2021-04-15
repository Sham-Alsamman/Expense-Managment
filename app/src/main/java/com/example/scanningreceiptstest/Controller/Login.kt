package com.example.scanningreceiptstest.Controller

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.example.scanningreceiptstest.R
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.profile_activity.*
import java.util.regex.Matcher
import java.util.regex.Pattern

class Login : NavDrawerActivity ()  {

    val reg: String = "\\p{Punct}"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val PhoneNum= findViewById<TextInputLayout>(R.id.phoneNumET)
        val Password= findViewById<TextInputLayout>(R.id.PasswordEt)

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

    fun SignUpTextView(view: View) {
        val i = Intent(applicationContext, SignUp::class.java)
        startActivity(i)
    }
    fun Login(view: View) {
        // val i = Intent(applicationContext, ::class.java)
        //startActivity(i)
    }

    fun PhoneErrors(text: CharSequence?) {

    }
    fun PasswordErrors(text: CharSequence?){

    }


/* CHECK THE NAMES OF THE VIEWS!! (causing error)
    fun ShowHidePass(view: View) {

        if(view.getId()==R.id.ShowPassword){

            if(PasswordEt.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
                ShowPassword.setImageResource(R.drawable.ic_baseline_visibility_off_24);

                //Show Password
                PasswordEt.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }
            else{
                //Hide Password
                ShowPassword.setImageResource(R.drawable.ic_baseline_remove_red_eye_24);
                PasswordEt.setTransformationMethod(PasswordTransformationMethod.getInstance());

            }
        }


    }*/
}