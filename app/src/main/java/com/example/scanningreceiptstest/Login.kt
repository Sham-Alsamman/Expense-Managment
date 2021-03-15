package com.example.scanningreceiptstest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_login1.*

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login1)


    }

    fun SignUpTextView(view: View) {
        val i = Intent(applicationContext, SignUo::class.java)
        startActivity(i)

    }

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


    }
}