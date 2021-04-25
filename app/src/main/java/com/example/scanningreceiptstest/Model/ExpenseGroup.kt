package com.example.scanningreceiptstest.Model

import com.example.scanningreceiptstest.database.DBExpenseGroup

class ExpenseGroup(val groupID: String , val Partners:MutableList<Person>) {

    // here i want to declare list of person
    private val _Partners :MutableList<Person> = mutableListOf<Person>()
    var ID: String="" ;


    /******* remove
    private val _categoryList :MutableList<String> = mutableListOf<String>()
    val categoryList:List<String>
        get() = _categoryList*/

    fun AddPartners(PhoneNumber :String ){
        // here should search in firebase about the phone number if exist or not
        // if exist we add the person to the partners list
     //add new partners to list of person
       // _Partners.add();
    }

    fun GetTransactionHistory(Filter :String){ // return type list of transaction
      // call getTransaction() mehtod  from person to retrive list of transaction

        //call function filltering() in this class to filter the transaction depend on what user need

    }
    fun Filtering( ){ // should take two parameter transaction list to filter it and filter to filter list  depend on it

    // filtering the transction list depend on parameter

          //Example to filter list :
       // var month: MutableList<String> = mutableListOf("January", "February", "March")
        // to get the result as list
       // var monthList: MutableList<String> = month.filter { s -> s == "January" } as MutableList<String>

        // return filter list
    }

    fun toDBExpenseGroup(): DBExpenseGroup {
        return DBExpenseGroup(
            groupID,
            _Partners
        )
    }

}

/**** Create DBExpenseGroup data class in database package
 * contains the group id and a list of partners "IDs" only
 * all fields will be declared in the constructor with val keyword
 * for example, see Invitation class and DBInvitation class
 *
 * create extension methods in both classes to convert from the original class to the DB class and vice versa
 * (see Invitation classes for more details)
 ****/
