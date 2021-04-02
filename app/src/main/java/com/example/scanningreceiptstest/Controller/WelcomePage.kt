package com.example.scanningreceiptstest.Controller

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.scanningreceiptstest.R


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