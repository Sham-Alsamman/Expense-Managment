package com.example.scanningreceiptstest.Controller

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.scanningreceiptstest.Model.Invitation
import com.example.scanningreceiptstest.Model.InvitationStatus
import com.example.scanningreceiptstest.R
import com.example.scanningreceiptstest.database.DBInvitation
import com.example.scanningreceiptstest.database.Database
import kotlinx.android.synthetic.main.activity_invite_partner.*

class InvitePartner : NavDrawerActivity() {

    private var validPhoneNum = false
        /*set(value) {
            field = value
            if(validPhoneNum && !phoneNumExist)
                sendToPartner(invitePhoneNumET.editText?.text.toString())
        }*/
    private var phoneNumExist = true
        set(value) {
            field = value
            if(validPhoneNum && !phoneNumExist)
                sendToPartner(invitePhoneNumET.editText?.text.toString())
        }

    private fun updateList(list: List<DBInvitation>) {
        //Toast.makeText(this, "list updated ${list.size}", Toast.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_invite_partner)
        onCreateDrawer()
    }

    fun sendInvitation(view: View) {
        validPhoneNum = false
        val phoneNum = invitePhoneNumET.editText?.text

        isPartnerExist(phoneNum.toString())

        if(phoneNum?.isBlank() == true){
            invitePhoneNumET.setEndIconDrawable(R.drawable.ic_baseline_error_24)
            invitePhoneNumET.error = "Enter your partner's phone number"
        }
        else if (phoneNum?.startsWith('+') == false){
            invitePhoneNumET.setEndIconDrawable(R.drawable.ic_baseline_error_24)
            invitePhoneNumET.error = "Enter a complete phone number with country code (ex. +999795555555)"
        }
        /*else if (!isPartnerExist(invitePhoneNumET.editText?.text.toString())){
            invitePhoneNumET.setEndIconDrawable(R.drawable.ic_baseline_error_24)
            invitePhoneNumET.error = "This phone number is not registered in the app"
        }*/
        else{
            validPhoneNum = true
            invitePhoneNumET.setEndIconDrawable(0)
            invitePhoneNumET.error = ""
        }
    }

    private fun isPartnerExist(phoneNum: String) {
        //check if the phone number exists in the database
        Database.checkIfUserExist(phoneNum, ::partnerExists)
       // return true
    }

    private fun partnerExists(exist: Boolean){
        if(exist) {
            phoneNumExist = true
            Toast.makeText(this, "exist", Toast.LENGTH_SHORT).show()
        }else {
            phoneNumExist = false
            Toast.makeText(this, "Phone num does not exist", Toast.LENGTH_SHORT).show()
        }
    }

    private fun sendToPartner(phoneNum: String) {
        //search for the number in the database
        //https://www.youtube.com/watch?v=I84JmwBRJTY
        //https://blog.usejournal.com/send-device-to-device-push-notifications-without-server-side-code-238611c143

        val i = Invitation("sham", phoneNum, "123456", InvitationStatus.NEW)

        Database.sendInvitation(i.toDBInvitation())

        //Database.getAllInvitations(phoneNum, ::updateList)

        //if found send notification to that user using the user's token??
        Toast.makeText(this, "Invitation sent successfully", Toast.LENGTH_LONG).show()
        //else
        // Toast.makeText(this, "This phone number does not exist, tell your partner to sign up in this app", Toast.LENGTH_LONG).show()
    }

    /************************/
    companion object{
        fun getInvitations(list: List<DBInvitation>){
            Log.i("DBgetInvitations", "${list.size}")
            if (list.isNotEmpty())
                Log.i("DB", list[0]?.senderName.toString())
        }
    }
}
