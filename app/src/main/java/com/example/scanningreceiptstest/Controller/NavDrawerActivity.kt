package com.example.scanningreceiptstest.Controller

import android.content.Intent
import android.view.MenuItem
import androidx.annotation.VisibleForTesting
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.example.scanningreceiptstest.R
import com.example.scanningreceiptstest.SaveSharedPreference
import com.example.scanningreceiptstest.cancelSalaryAlarm
import com.example.scanningreceiptstest.database.CURRENT_GROUP
import com.example.scanningreceiptstest.database.CURRENT_USER
import com.example.scanningreceiptstest.database.FirebaseDatabase
import com.example.scanningreceiptstest.database.IDatabase
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.nav_header.view.*
import kotlinx.android.synthetic.main.navigtion_drawer.*
import kotlinx.android.synthetic.main.toolbar.*

open class NavDrawerActivity : AppCompatActivity() {

    var database: IDatabase = FirebaseDatabase
        @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE) set

    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
        //add toolbar:
        setSupportActionBar(toolbar)
    }

    fun onCreateDrawer() {
        //navigation drawer:
        val drawerToggle = ActionBarDrawerToggle(
            this, drawerLayout,
            R.string.openDrawer,
            R.string.closeDrawer
        )
        drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        //set the user name in the navigation view header:
        CURRENT_USER?.let { navView.getHeaderView(0).userNameTV.text = CURRENT_USER!!.name }

        navView.setNavigationItemSelectedListener {
            navDrawerClickListener(it)
        }
    }

    private fun navDrawerClickListener(item: MenuItem): Boolean {
        var intent: Intent? = null

        when (item.itemId) {
            R.id.navMenu_editProfile -> {
                intent = Intent(applicationContext, Profile::class.java)
            }
            R.id.navMenu_home -> {
                intent = Intent(applicationContext, Home::class.java)
            }
            R.id.navMenu_wallet -> {
                intent = Intent(applicationContext, Wallet::class.java)
            }
            R.id.navMenu_report -> {
                intent = Intent(applicationContext, Report::class.java)
            }
            R.id.navMenu_transactionHistory -> {
                intent = Intent(applicationContext, TransactionHistory::class.java)
            }
            R.id.navMenu_invitations -> {
                intent = Intent(applicationContext, Invitations::class.java)
            }
            R.id.navMenu_invitePartner -> {
                intent = Intent(applicationContext, InvitePartner::class.java)
            }
            R.id.navMenu_about -> {
                intent = Intent(applicationContext, About::class.java)
            }
            R.id.navMenu_logout -> {
                SaveSharedPreference.clearUserData(this)
                cancelSalaryAlarm(this)

                CURRENT_USER = null
                CURRENT_GROUP = null
                intent = Intent(applicationContext, Login::class.java)
            }
        }

        intent?.let {
            startActivity(it)
            finish()
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    //nav drawer icon (hamburger) click listener:
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (drawerLayout != null) {
            if (item.itemId == android.R.id.home) {
                drawerLayout.openDrawer(GravityCompat.START)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (drawerLayout != null) {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START)
            } else {
                super.onBackPressed()
            }
        } else
            super.onBackPressed()
    }
}

