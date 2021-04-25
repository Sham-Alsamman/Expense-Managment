package com.example.scanningreceiptstest.Model

import com.example.scanningreceiptstest.database.DBInvitation

enum class InvitationStatus {
    NEW,
    REVIEWED
}

class Invitation(val senderName: String, val receiverPhoneNum: String, val groupID: String, var invitationStatus: InvitationStatus) {
    val message: String
        get() = "$senderName invited you to join his/her expense group"

    // fun to convert form invitation object to database invitation object
    fun toDBInvitation() : DBInvitation {
        return DBInvitation(
            senderName,
            receiverPhoneNum,
            groupID,
            invitationStatus
        )
    }
}


