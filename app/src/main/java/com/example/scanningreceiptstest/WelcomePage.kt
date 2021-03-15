package com.example.scanningreceiptstest

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity


class WelcomePage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcom_page2)



    }

    fun SignUpOnClick(view: View) {
       // val i = Intent(applicationContext, SignUo::class.java)
       // startActivity(i)
    }

    fun SignInOnClick(view: View) {
       // val i = Intent(applicationContext, Login::class.java)
       // startActivity(i)

    }
}