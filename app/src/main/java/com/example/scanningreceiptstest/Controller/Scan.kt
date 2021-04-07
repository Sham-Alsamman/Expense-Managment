package com.example.scanningreceiptstest.Controller

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.scanningreceiptstest.R


class Scan : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan)


    }

    fun openCamera(view: View)
    {
        val intent = Intent("android.media.action.IMAGE_CAPTURE")
        startActivity(intent)
    }



    }