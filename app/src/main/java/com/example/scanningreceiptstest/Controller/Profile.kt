//package com.example.scanningreceiptstest
package com.example.scanningreceiptstest.Controller


import android.app.Person
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.scanningreceiptstest.Model.Transaction
import com.example.scanningreceiptstest.R
import com.example.scanningreceiptstest.database.Database
import kotlinx.android.synthetic.main.profile_activity.*

class Profile : NavDrawerActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile_activity)
        onCreateDrawer()
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
        }
    }

    private fun saveInfo()
    {

    }
}