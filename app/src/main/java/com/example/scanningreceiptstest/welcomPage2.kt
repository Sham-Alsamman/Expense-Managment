package com.example.scanningreceiptstest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class welcomPage2 : AppCompatActivity() {
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