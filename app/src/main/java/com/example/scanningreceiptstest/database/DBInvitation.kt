package com.example.scanningreceiptstest.database

import com.example.scanningreceiptstest.Model.Invitation
import com.example.scanningreceiptstest.Model.InvitationStatus

data class DBInvitation(
    var id: String,
    val senderName: String,
    val receiverPhoneNum: String,
    val groupID: String,
    val invitationStatus: InvitationStatus
){
    constructor() : this("", "", "", "", InvitationStatus.NEW)
}

//extension fun to convert form database invitation object to invitation object
//fun <class_name>.<method_name>(): <return_type> {}
fun DBInvitation.toInvitation(): Invitation {
    return Invitation(
        id,
        senderName,
        receiverPhoneNum,
        groupID,
        invitationStatus
    )
}

fun List<DBInvitation>.toInvitationList(): List<Invitation> {
    val invitationsList = mutableListOf<Invitation>()
    for (i in this){
        invitationsList.add(i.toInvitation())
    }
    return invitationsList
}