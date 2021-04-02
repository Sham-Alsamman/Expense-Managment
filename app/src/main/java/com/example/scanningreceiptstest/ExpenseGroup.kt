class ExpenseGroup {

    // here i want to declare list of person

   // private val _Partners :MutableList<> = mutableListOf<>()
  //  val Partners:List<>
     //   get() = _Partners

    var ID: String="" ;
    private val _categoryList :MutableList<String> = mutableListOf<String>()
    val categoryList:List<String>
        get() = _categoryList

    fun AddPartners(PhoneNumber :String ){
     //add phone number to list of person
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
