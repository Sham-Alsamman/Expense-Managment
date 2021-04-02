package com.example.scanningreceiptstest.Controller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import com.example.scanningreceiptstest.R
import kotlinx.android.synthetic.main.main_menu_test.*

class TestActivity : AppCompatActivity() {

    /*private lateinit var drawer: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_menu_test)

        setSupportActionBar(toolbar)
        val drawerToggle = ActionBarDrawerToggle(this, drawerLayout,
            R.string.app_name,
            R.string.app_name
        )
        drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        /*val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        drawer = findViewById(R.id.drawerLayout)

        toggle = ActionBarDrawerToggle(this, drawer, toolbar,  R.string.app_name, R.string.app_name)
        drawer.addDrawerListener(toggle)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)*/
    }

    //nav drawer click listener:
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                drawerLayout.openDrawer(GravityCompat.START)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

}