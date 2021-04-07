package com.example.scanningreceiptstest.Controller

import android.content.Intent
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.example.scanningreceiptstest.Model.Home
import com.example.scanningreceiptstest.Model.recEnum
import com.example.scanningreceiptstest.R
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.profile_activity.*
import java.util.regex.Matcher
import java.util.regex.Pattern


class Login : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val PhoneNum = findViewById<TextInputLayout>(R.id.phoneNumET)
        val Password =findViewById<TextInputLayout>(R.id.PasswordEt)
        val reg:String = "\\p{Punct}"
        //val text1: String = PhoneNum.editText?.text.toString()

        /*PhoneNum.editText?.doOnTextChanged { text, start, before, count ->
            if(text.isNullOrEmpty()){
                phoneNumET.setEndIconDrawable(R.drawable.ic_baseline_error_24)
                phoneNumET.error = "Enter Your Phone Number "
            }
            else if(!text.startsWith("079") && !text.startsWith("078") && !text.startsWith("077")){
                phoneNumET.setEndIconDrawable(R.drawable.ic_baseline_error_24)
                phoneNumET.error ="Should Start with 077 or 078 or 079 "
            }
            else if(text.length<PhoneETSignUp.counterMaxLength) {
                phoneNumET.setEndIconDrawable(R.drawable.ic_baseline_error_24)
                phoneNumET.error = "Phone number must be 10 numbers long"
            }
            else if(text.length>phoneNumET.counterMaxLength) {
                phoneNumET.setEndIconDrawable(R.drawable.ic_baseline_error_24)
                phoneNumET.error = "Error"
            }
            else{
                phoneNumET.setEndIconDrawable(0)

                phoneNumET.error = null
            }
        }

        Password.editText?.doOnTextChanged { text, start, before, count ->
            val pattern = Pattern.compile(reg)
            val matcher: Matcher = pattern.matcher(text)
            if(matcher.find()){
                PasswordEt.setEndIconActivated(false)
               // PasswordEt.setEndIconDrawable(R.drawable.ic_baseline_error_24)
               PasswordEt.error="Password Should Contain Only Characters and Numbers"
               }
           else  if(text.isNullOrEmpty()){
              //  PasswordEt.setEndIconDrawable(R.drawable.ic_baseline_error_24)
                PasswordEt.error = "Enter Password"
            }
            else{
                //PasswordEt.setEndIconDrawable(0)
                PasswordEt.error = null

            }

        }*/



    }

    fun SignUpTextView(view: View) {
        val i = Intent(applicationContext, SignUp::class.java)
        startActivity(i)

    }

    fun Login(view: View) {
       // val i = Intent(applicationContext, ::class.java)
        //startActivity(i)
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