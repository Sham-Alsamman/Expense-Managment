package com.example.scanningreceiptstest.Controller

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.scanningreceiptstest.R
import kotlinx.android.synthetic.main.activity_invite_partner.*

class InvitePartner : NavDrawerActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_invite_partner)
        onCreateDrawer()
    }

    fun sendInvitation(view: View) {
        if(invitePhoneNumET.editText?.text?.isBlank() == true){
            invitePhoneNumET.setEndIconDrawable(R.drawable.ic_baseline_error_24)
            invitePhoneNumET.error = "Enter your partner's phone number"
        }
        else if (invitePhoneNumET.editText?.text?.startsWith('+') == false){
            invitePhoneNumET.setEndIconDrawable(R.drawable.ic_baseline_error_24)
            invitePhoneNumET.error = "Enter a complete phone number with country code (ex. +999795555555)"
        }
        else{
            invitePhoneNumET.setEndIconDrawable(0)
            invitePhoneNumET.error = ""
            sendToPartner(invitePhoneNumET.editText?.text.toString())
        }
    }

    private fun sendToPartner(phoneNum: String) {
        //search for the number in the database
        //https://www.youtube.com/watch?v=I84JmwBRJTY
        //https://blog.usejournal.com/send-device-to-device-push-notifications-without-server-side-code-238611c143
        //if found send notification to that user using the user's token??
        Toast.makeText(this, "Invitation sent successfully", Toast.LENGTH_LONG).show()
        //else
        // Toast.makeText(this, "This phone number does not exist, tell your partner to sign up in this app", Toast.LENGTH_LONG).show()
    }
}
