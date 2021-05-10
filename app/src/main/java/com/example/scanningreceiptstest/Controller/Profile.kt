package com.example.scanningreceiptstest.Controller


import android.os.Bundle
import android.widget.EditText
import com.example.scanningreceiptstest.R
import com.example.scanningreceiptstest.database.CURRENT_USER
import com.example.scanningreceiptstest.database.Database
import com.example.scanningreceiptstest.Model.Person
import kotlinx.android.synthetic.main.profile_activity.*

class Profile : NavDrawerActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile_activity)
        onCreateDrawer()

        var userName=findViewById<EditText>(R.id.edit_text)
        userName.setText(CURRENT_USER!!.name)

        var phone=findViewById<EditText>(R.id.phoneNum)
        phone.setText(CURRENT_USER!!.phoneNumber)

        //save new password
       /* var pass=findViewById<EditText>(R.id.changePass)
        pass.setText(CURRENT_USER!!.)*/


        SaveText.setOnClickListener {
            //saveInfo()
/*
            val listTransaction = mutableListOf<Transaction>()
            val m = com.example.scanningreceiptstest.Model.Person(
                "+962791558798",
                "Malak",
                "4",
                1000.500,
                1000.00,
                100.00,
                100.00,
                listTransaction
            )
            Database.updateUserInfo("+962791558798",m.toDBPerson())
 */
            var userName2=findViewById<EditText>(R.id.edit_text).text.toString()

            var phone2=findViewById<EditText>(R.id.phoneNum).text.toString()

            var person=Person(phone2,userName2, CURRENT_USER!!.password, CURRENT_USER!!.groupId,CURRENT_USER!!.monthlySalary,CURRENT_USER!!.totalIncome,
                CURRENT_USER!!.savingAmount,CURRENT_USER!!.savingWallet,CURRENT_USER!!.transactions)

            Database.updateUserInfo(phone2,person.toDBPerson())

        }
    }

    private fun saveInfo()
    {

    }
}