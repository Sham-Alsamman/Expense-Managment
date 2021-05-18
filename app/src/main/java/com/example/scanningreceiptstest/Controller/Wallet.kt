package com.example.scanningreceiptstest.Controller

import android.os.Bundle
import com.example.scanningreceiptstest.Model.Person
import com.example.scanningreceiptstest.R
import com.example.scanningreceiptstest.database.*
import kotlinx.android.synthetic.main.wallet.*


class Wallet : NavDrawerActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.wallet)
        onCreateDrawer()
        //monthlyIncome.text =

        /// set text textView2

        if(!CURRENT_USER!!.totalIncome.isNaN())
            monthlyIncome.setText( CURRENT_USER!!.totalIncome.toString() )

        if(!CURRENT_USER!!.savingAmount.isNaN())
            savingRate.setText( CURRENT_USER!!.savingAmount.toString() )

        saveIncome.setOnClickListener {
            saveData(monthlyIncome.text.toString().toDouble(), savingRate.text.toString().toDouble()  )
        }

        Database.getUser(CURRENT_USER!!.phoneNumber, ::onPerson)

    }

    private fun saveData(monthlyInc : Double, savingRa : Double){
        CURRENT_USER!!.monthlySalary = monthlyInc
        CURRENT_USER!!.savingAmount = savingRa
    }


    private fun onPerson(p : DBPerson) {
        Database.updateUserInfo(CURRENT_USER!!.phoneNumber, p)
    }

}