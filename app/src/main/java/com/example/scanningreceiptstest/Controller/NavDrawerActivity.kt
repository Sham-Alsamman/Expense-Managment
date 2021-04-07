package com.example.scanningreceiptstest.Controller

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.example.scanningreceiptstest.R
import kotlinx.android.synthetic.main.main_menu_test.*

open class NavDrawerActivity : AppCompatActivity() {

     fun onCreateDrawer() {
        //navigation drawer:
        val drawerToggle = ActionBarDrawerToggle(this, drawerLayout,
            R.string.openDrawer,
            R.string.closeDrawer
        )
        drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navView.setNavigationItemSelectedListener {
            navDrawerClickListener(it)
        }
    }

    private fun navDrawerClickListener(item: MenuItem) : Boolean{
        var intent : Intent? = null

        when(item.itemId){
            R.id.navMenu_wallet -> {
                intent = Intent(applicationContext, Wallet::class.java)
            }
            R.id.navMenu_report -> {
                intent = Intent(applicationContext, Report::class.java)
            }
            R.id.navMenu_transactionHistory -> {
                intent = Intent(applicationContext, TransactionHistory::class.java)
            }
            R.id.navMenu_notification -> {
                intent = Intent(applicationContext, Notification::class.java)
            }
            R.id.navMenu_invitePartner -> {
                intent = Intent(applicationContext, InvitePartner::class.java)
            }
            R.id.navMenu_about -> {
                intent = Intent(applicationContext, About::class.java)
            }
            R.id.navMenu_logout -> {
                intent = Intent(applicationContext, Login::class.java)
            }
        }

        intent?.let {
            startActivity(it)
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
        //add toolbar:
        setSupportActionBar(toolbar)
    }

    //nav drawer icon (hamburger) click listener:
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

