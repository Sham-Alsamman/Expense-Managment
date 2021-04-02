package com.example.scanningreceiptstest.Controller

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.scanningreceiptstest.R

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


    }

    fun SignUpTextView(view: View) {
        val i = Intent(applicationContext, SignUp::class.java)
        startActivity(i)

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