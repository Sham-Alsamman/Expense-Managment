package com.example.scanningreceiptstest.Model

import com.example.scanningreceiptstest.database.*

class ExpenseGroup(val groupID: String, partners: MutableList<String> = mutableListOf()) {

    // here i want to declare list of person
    private val _partners: MutableList<String> = partners
    val partners: List<String>
        get() = _partners


    fun addPartner(PhoneNumber: String) {
        // here should search in firebase about the phone number if exist or not
        // if exist we add the person to the partners list
        //add new partners to list of person
         _partners.add(PhoneNumber);
    }

    fun toDBExpenseGroup(): DBExpenseGroup {
        return DBExpenseGroup(
            groupID,
            _partners
        )
    }

}
