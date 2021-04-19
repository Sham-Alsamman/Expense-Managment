package com.example.scanningreceiptstest.Model

class ExpenseGroup {

    // here i want to declare list of person
    private val _Partners :MutableList<Person> = mutableListOf<Person>()
    val Partners:List<Person>
       get() = _Partners

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


}
