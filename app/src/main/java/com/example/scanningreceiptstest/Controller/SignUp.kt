package com.example.scanningreceiptstest.Controller

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.scanningreceiptstest.R

class SignUp : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
    }

    fun SignInTextView(view: View) {
        val i = Intent(applicationContext, Login::class.java)
        startActivity(i)
    }
}