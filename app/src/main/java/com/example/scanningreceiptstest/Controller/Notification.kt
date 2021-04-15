package com.example.scanningreceiptstest.Controller

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.scanningreceiptstest.R

class Notification : NavDrawerActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)
        onCreateDrawer()
    }
}