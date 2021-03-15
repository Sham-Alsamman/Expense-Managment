package com.example.scanningreceiptstest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class SignUo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_uo)
    }

    fun SignInTextView(view: View) {
        val i = Intent(applicationContext, Login::class.java)
        startActivity(i)
    }
}