package com.example.scanningreceiptstest.Model

import com.example.scanningreceiptstest.database.*

class ExpenseGroup(val groupID: String, partners: MutableList<String> = mutableListOf()) {

    private val _partners: MutableList<String> = partners
    val partners: List<String>
        get() = _partners


    fun addPartner(PhoneNumber: String) {
        if (PhoneNumber.isNotEmpty())
             _partners.add(PhoneNumber)
    }

    fun removePartner(phoneNumber: String) {
        _partners.remove(phoneNumber)
    }

    fun toDBExpenseGroup(): DBExpenseGroup {
        return DBExpenseGroup(
            groupID,
            _partners
        )
    }
}
