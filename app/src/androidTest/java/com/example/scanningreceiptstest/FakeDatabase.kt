package com.example.scanningreceiptstest

import at.favre.lib.crypto.bcrypt.BCrypt
import com.example.scanningreceiptstest.database.*

class FakeDatabase : IDatabase{
    val users = mutableListOf<DBPerson>()
    val expenseGroups = mutableListOf<DBExpenseGroup>()
    val incomes = hashMapOf<String, MutableList<DBIncome>>()
    val expenses = hashMapOf<String, MutableList<DBExpense>>()
    val invitations = hashMapOf<String, MutableList<DBInvitation>>()

    override fun getUser(phoneNum: String, onDataRetrieved: (DBPerson) -> Unit) {
        val user = users.find {
            phoneNum == it.phoneNumber
        }

        if (user != null) {
            onDataRetrieved(user)
        }
    }

    override fun getExpenseGroup(groupId: String, onDataRetrieved: (DBExpenseGroup) -> Unit) {
        val group = expenseGroups.find {
            groupId == it.groupID
        }

        if (group != null) {
            onDataRetrieved(group)
        }
    }

    override fun checkPassword(phone: String, pass: String, onDataRetrieved: (Boolean) -> Unit) {
        val user = users.find {
            phone == it.phoneNumber
        }

        if (user != null) {
            onDataRetrieved(true)
        }else
            onDataRetrieved(false)
    }

    override fun checkIfUserExist(phoneNum: String, onDataRetrieved: (Boolean) -> Unit) {
        val user = users.find {
            phoneNum == it.phoneNumber
        }

        if (user != null)
            onDataRetrieved(true)
        else
            onDataRetrieved(false)
    }

    override fun sendInvitation(invitation: DBInvitation) {
        val inviteList = invitations[invitation.receiverPhoneNum]
        if (inviteList != null) {
            inviteList.add(invitation)
        }else {
            invitations[invitation.receiverPhoneNum] = mutableListOf(invitation)
        }
        //println("invite invetation retrieved ${invitations[invitation.receiverPhoneNum]?.size}")
    }

    override fun getAllInvitations(
        phoneNum: String,
        onDataRetrievedFun: (list: List<DBInvitation>) -> Unit
    ) {
        if (invitations[phoneNum] != null)
            onDataRetrievedFun(invitations[phoneNum]!!)
        else
            onDataRetrievedFun(listOf())

        //println("invite invetation retrieved ${invitations[phoneNum]?.size}")
    }

    override fun addNewUser(user: DBPerson, onAddedSuccessfully: (Boolean) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun addNewExpenseGroup(group: DBExpenseGroup): DBExpenseGroup {
        TODO("Not yet implemented")
    }

    override fun addNewExpense(phoneNum: String, expense: DBExpense) {
        TODO("Not yet implemented")
    }

    override fun addNewIncome(phoneNum: String, income: DBIncome) {
        TODO("Not yet implemented")
    }

    override fun getAllExpenses(
        phoneNum: String,
        onDataRetrievedFun: (list: List<DBExpense>) -> Unit
    ) {
        TODO("Not yet implemented")
    }

    override fun getAllIncomes(
        phoneNum: String,
        onDataRetrievedFun: (list: List<DBIncome>) -> Unit
    ) {
        TODO("Not yet implemented")
    }

    override fun updateUserInfo(user: DBPerson) {
        TODO("Not yet implemented")
    }

    override fun updateInvitation(phoneNum: String, invitation: DBInvitation) {
        TODO("Not yet implemented")
    }

    override fun updateExpenseGroup(group: DBExpenseGroup) {
        TODO("Not yet implemented")
    }

    override fun deleteExpenseGroup(groupId: String) {
        TODO("Not yet implemented")
    }

    fun checkIfInvitationExists(receiverNum: String, senderName: String) : Boolean {
        val invite = invitations[receiverNum]?.find {
            it.senderName == senderName
        }

        if (invite != null)
            return true
        return false
    }
}