package com.example.scanningreceiptstest.Controller

import android.content.Intent
import android.os.Bundle
import com.example.scanningreceiptstest.R
import com.example.scanningreceiptstest.database.*
import kotlinx.android.synthetic.main.wallet.*


class Wallet : NavDrawerActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.wallet)
        onCreateDrawer()

        /// set text textView2

        if(!CURRENT_USER!!.totalIncome.isNaN())
            textView2.setText( CURRENT_USER!!.savingWallet.toString() )

        if(!CURRENT_USER!!.monthlySalary.isNaN())
            monthlyIncome.setText( CURRENT_USER!!.monthlySalary.toString())

        if(!CURRENT_USER!!.savingAmount.isNaN())
            savingRate.setText( CURRENT_USER!!.savingAmount.toString() )

        saveIncome.setOnClickListener {
            saveData(monthlyIncome.text.toString().toDouble(), savingRate.text.toString().toDouble()  )
            var intent = Intent(applicationContext, Home::class.java)
            intent?.let {startActivity(it)}

        }

        Database.getUser(CURRENT_USER!!.phoneNumber, ::onPerson)

    }

    private fun saveData(monthlyInc : Double, savingRa : Double){
        CURRENT_USER!!.monthlySalary = monthlyInc
        CURRENT_USER!!.totalIncome += monthlyInc
        CURRENT_USER!!.remaining += monthlyInc
        CURRENT_USER!!.savingAmount = savingRa
        Database.updateUserInfo(DBPerson(CURRENT_USER!!.phoneNumber, CURRENT_USER!!.name, CURRENT_USER!!.password, CURRENT_USER!!.groupId, CURRENT_USER!!.monthlySalary, CURRENT_USER!!.totalIncome, CURRENT_USER!!.savingAmount, CURRENT_USER!!.savingWallet, CURRENT_USER!!.remaining))
    }


    private fun onPerson(p : DBPerson) {
        Database.updateUserInfo(p)
    }

}