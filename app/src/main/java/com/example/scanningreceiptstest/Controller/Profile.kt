//package com.example.scanningreceiptstest
package com.example.scanningreceiptstest.Controller


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.scanningreceiptstest.R
import kotlinx.android.synthetic.main.profile_activity.*

class Profile : NavDrawerActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile_activity)
        SaveText.setOnClickListener {
            SaveInfo()
        }
    }

    private fun SaveInfo()
    {

    }
}