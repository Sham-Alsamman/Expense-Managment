package com.example.scanningreceiptstest.Controller

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.example.scanningreceiptstest.R
import com.example.scanningreceiptstest.SaveSharedPreference
import com.example.scanningreceiptstest.database.*

class welcomPage2 : NavDrawerActivity () {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcom_page2)

        checkIfUserLoggedIn()
    }

    private fun checkIfUserLoggedIn() {
        val userId = SaveSharedPreference.getUserId(this)
        val groupId = SaveSharedPreference.getGroupId(this)

        if (userId != null && groupId != null){
            database.getUser(userId, ::onDBUserResult)
            database.getExpenseGroup(groupId, ::onDBGroupResult)
        }
    }

    private fun onDBGroupResult(dbExpenseGroup: DBExpenseGroup) {
        CURRENT_GROUP = dbExpenseGroup.toExpenseGroup()
    }

    private fun onDBUserResult(dbPerson: DBPerson) {
        CURRENT_USER = dbPerson.toPerson()
        startActivity(Intent(this, Home::class.java))
        finish()
    }

    fun SignUpOnClick(view: View) {
         val i = Intent(applicationContext, SignUp::class.java)
         startActivity(i)
    }

    fun SignInOnClick(view: View) {
         val i = Intent(applicationContext, Login::class.java)
         startActivity(i)

    }
}