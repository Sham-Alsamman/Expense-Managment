package com.example.scanningreceiptstest.Controller

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.scanningreceiptstest.R
import kotlinx.android.synthetic.main.activity_invite_partner.*

class InvitePartner : NavDrawerActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_invite_partner)
        onCreateDrawer()
    }

    fun sendInvitation(view: View) {
        if(android.util.Patterns.PHONE.matcher(invitePhoneNumET.editText?.text).matches())
            Toast.makeText(this, "true", Toast.LENGTH_SHORT).show()
        else
            Toast.makeText(this, "true", Toast.LENGTH_SHORT).show()
    }
}
