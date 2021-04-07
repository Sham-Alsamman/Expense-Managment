package com.example.scanningreceiptstest.Controller

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.example.scanningreceiptstest.R
import kotlinx.android.synthetic.main.main_menu_test.*

class Wallet : NavDrawerActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.wallet)
        onCreateDrawer()
    }

}