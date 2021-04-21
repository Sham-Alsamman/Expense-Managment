package com.example.scanningreceiptstest.Controller

import android.os.Bundle
import com.example.scanningreceiptstest.R

class Invitations : NavDrawerActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_invitations)
        onCreateDrawer()
    }
}