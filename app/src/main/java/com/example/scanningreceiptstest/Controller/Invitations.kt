package com.example.scanningreceiptstest.Controller

import android.os.Bundle
import android.widget.Toast
import com.example.scanningreceiptstest.Controller.recyclerViewAdapters.InvitationAdapter
import com.example.scanningreceiptstest.Controller.recyclerViewAdapters.InvitationClickListener
import com.example.scanningreceiptstest.Model.Invitation
import com.example.scanningreceiptstest.Model.InvitationStatus
import com.example.scanningreceiptstest.R
import com.example.scanningreceiptstest.database.*
import kotlinx.android.synthetic.main.activity_invitations.*

class Invitations : NavDrawerActivity() {

    private lateinit var recyclerAdapter: InvitationAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_invitations)
        onCreateDrawer()

        recyclerAdapter = InvitationAdapter(InvitationClickListener(::onJoinClicked, ::onCancelClicked))
        invitationRecyclerView.adapter = recyclerAdapter

        getInvitations()
    }

    private fun getInvitations() {
        Database.getAllInvitations(CURRENT_USER!!.phoneNumber, ::onDBResult)
    }

    private fun onDBResult(list: List<DBInvitation>) {
        recyclerAdapter.invitationsList = list.toInvitationList()
        Toast.makeText(this, "invitations updated ${list.size}", Toast.LENGTH_SHORT).show()
    }


    private fun onJoinClicked(invitation: Invitation) {
        invitation.invitationStatus = InvitationStatus.REVIEWED
        Database.updateInvitation(CURRENT_USER!!.phoneNumber, invitation.toDBInvitation())

        Database.getExpenseGroup(invitation.groupID, ::onDBExpenseGroupResult)
    }

    private fun onCancelClicked(invitation: Invitation) {
        invitation.invitationStatus = InvitationStatus.REVIEWED
        Database.updateInvitation(CURRENT_USER!!.phoneNumber, invitation.toDBInvitation())
    }

    private fun onDBExpenseGroupResult(newGroup: DBExpenseGroup) {
        //remove user from the current group:
        CURRENT_GROUP!!.removePartner(CURRENT_USER!!.phoneNumber)
        Database.updateExpenseGroup(CURRENT_GROUP!!.toDBExpenseGroup())
        /*******delete group if empty?********/

        CURRENT_USER!!.changeGroup(newGroup.groupID)
        Database.updateUserInfo(CURRENT_USER!!.toDBPerson())

        //change the current group:
        CURRENT_GROUP = newGroup.toExpenseGroup()
        CURRENT_GROUP!!.addPartner(CURRENT_USER!!.phoneNumber)
        Database.updateExpenseGroup(CURRENT_GROUP!!.toDBExpenseGroup())
        Toast.makeText(this, "Expense group changed successfully", Toast.LENGTH_SHORT).show()

        SaveSharedPreference.saveUserData(this)
    }
}