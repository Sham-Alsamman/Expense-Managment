package com.example.scanningreceiptstest.Controller

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.scanningreceiptstest.Model.Invitation
import com.example.scanningreceiptstest.Model.InvitationStatus
import com.example.scanningreceiptstest.R
import com.example.scanningreceiptstest.database.CURRENT_GROUP
import com.example.scanningreceiptstest.database.CURRENT_USER
import kotlinx.android.synthetic.main.activity_invite_partner.*

class InvitePartner : NavDrawerActivity() {

    private var validPhoneNum = false
    private var phoneNumExist = false
        set(value) {
            field = value
            if(validPhoneNum && phoneNumExist)
                sendToPartner(invitePhoneNumET.editText?.text.toString())
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_invite_partner)
        onCreateDrawer()
    }

    /*invite button click listener*/
    fun sendInvitation(view: View) {
        validPhoneNum = false
        val phoneNum = invitePhoneNumET.editText?.text

        if(phoneNum?.isBlank() == true){
            invitePhoneNumET.setEndIconDrawable(R.drawable.ic_baseline_error_24)
            invitePhoneNumET.error = "Enter your partner's phone number"
        }
        else if (phoneNum?.startsWith('+') == false){
            invitePhoneNumET.setEndIconDrawable(R.drawable.ic_baseline_error_24)
            invitePhoneNumET.error = "Enter a complete phone number with country code (ex. +999795555555)"
        }
        else{
            validPhoneNum = true
            invitePhoneNumET.setEndIconDrawable(0)
            invitePhoneNumET.error = "Sending..."
            checkIfPartnerExist(phoneNum.toString())
        }
    }

    private fun checkIfPartnerExist(phoneNum: String) {
        //check if the phone number exists in the database
        database.checkIfUserExist(phoneNum, ::onDBResult)
    }

    private fun onDBResult(exist: Boolean){
        if(exist) {
            phoneNumExist = true
        }else {
            phoneNumExist = false
            invitePhoneNumET.setEndIconDrawable(R.drawable.ic_baseline_error_24)
            invitePhoneNumET.error = "This phone number is not registered in the app"
        }
    }

    private fun sendToPartner(phoneNum: String) {
        if (CURRENT_USER != null && CURRENT_GROUP != null) {
            val invitation = Invitation("", CURRENT_USER!!.name, phoneNum, CURRENT_GROUP!!.groupID, InvitationStatus.NEW)
            database.sendInvitation(invitation.toDBInvitation())

            Toast.makeText(this, "Invitation sent successfully", Toast.LENGTH_SHORT).show()

            invitePhoneNumET.setEndIconDrawable(0)
            invitePhoneNumET.error = null
        }
        else
            Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT).show()
    }
}
