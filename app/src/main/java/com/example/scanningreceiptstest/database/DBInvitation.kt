package com.example.scanningreceiptstest.database

import com.example.scanningreceiptstest.Model.Invitation
import com.example.scanningreceiptstest.Model.InvitationStatus

data class DBInvitation(
    val senderName: String,
    val receiverPhoneNum: String,
    val groupID: String,
    val invitationStatus: InvitationStatus
)

//extension fun to convert form database invitation object to invitation object
//fun <class_name>.<method_name>(): <return_type> {}
fun DBInvitation.toInvitation(): Invitation {
    return Invitation(
        senderName,
        receiverPhoneNum,
        groupID,
        invitationStatus
    )
}