package com.example.scanningreceiptstest.Controller

import android.os.Bundle
import com.example.scanningreceiptstest.R

class About : NavDrawerActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.acticity_about)
        //add navigation drawer
        onCreateDrawer()
    }

}