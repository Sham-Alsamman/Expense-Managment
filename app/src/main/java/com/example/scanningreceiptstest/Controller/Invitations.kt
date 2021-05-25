package com.example.scanningreceiptstest.Controller

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.scanningreceiptstest.Controller.recyclerViewAdapters.InvitationAdapter
import com.example.scanningreceiptstest.Controller.recyclerViewAdapters.InvitationClickListener
import com.example.scanningreceiptstest.Model.Invitation
import com.example.scanningreceiptstest.Model.InvitationStatus
import com.example.scanningreceiptstest.R
import com.example.scanningreceiptstest.SaveSharedPreference
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
        database.getAllInvitations(CURRENT_USER!!.phoneNumber, ::onDBResult)
    }

    private fun onDBResult(list: List<DBInvitation>) {
        if (list.isEmpty()) {
            noData_Invitation.visibility = View.VISIBLE
        } else {
            noData_Invitation.visibility = View.GONE
            recyclerAdapter.invitationsList = list.toInvitationList()
        }
    }

    private fun onJoinClicked(invitation: Invitation) {
        invitation.invitationStatus = InvitationStatus.REVIEWED
        database.updateInvitation(CURRENT_USER!!.phoneNumber, invitation.toDBInvitation())

        database.getExpenseGroup(invitation.groupID, ::onDBExpenseGroupResult)
    }

    private fun onCancelClicked(invitation: Invitation) {
        invitation.invitationStatus = InvitationStatus.REVIEWED
        database.updateInvitation(CURRENT_USER!!.phoneNumber, invitation.toDBInvitation())
    }

    private fun onDBExpenseGroupResult(newGroup: DBExpenseGroup) {
        //remove user from the current group:
        CURRENT_GROUP!!.removePartner(CURRENT_USER!!.phoneNumber)
        //delete group if empty
        if (CURRENT_GROUP!!.partners.isEmpty())
            database.deleteExpenseGroup(CURRENT_GROUP!!.groupID)
        else
            database.updateExpenseGroup(CURRENT_GROUP!!.toDBExpenseGroup())

        //change the user's group
        CURRENT_USER!!.changeGroup(newGroup.groupID)
        database.updateUserInfo(CURRENT_USER!!.toDBPerson())

        //change the current group:
        CURRENT_GROUP = newGroup.toExpenseGroup()
        CURRENT_GROUP!!.addPartner(CURRENT_USER!!.phoneNumber)
        database.updateExpenseGroup(CURRENT_GROUP!!.toDBExpenseGroup())
        Toast.makeText(this, "Expense group changed successfully", Toast.LENGTH_SHORT).show()

        SaveSharedPreference.saveUserData(this)
    }
}