package com.example.scanningreceiptstest.Model

class Person(userName: String, phoneNum: String) {
    var name: String = userName
    var phoneNumber: String = phoneNum

    var homeId: String = ""  //should be read only (val), auto value??

    var monthlySalary: Double = 0.0
        set(value){ //instead of setMonthlySalary() method
            if(value >= 0)
                field = value
        }

    var totalIncome: Double = 0.0 // should be always positive num??
    var savingAmount: Double = 0.0
        set(value) { // instead of setSavingAmount() method
            if(value >= 0)
                field = value
        }

    var savingWallet: Double = 0.0
        set(value){
            if (value >= 0)
                field = value
        }
    private val _transactions = mutableListOf<Transaction>()
    /**instead of getTransactions method**/
    val transactions: List<Transaction>
        get()= _transactions


    //not here?
    fun acceptInvitation(homeId: String){

    }

    //not here?
    fun receiveInvite(homeId: String){

    }

    fun addExpense(expense: Expense){
        _transactions.add(expense)
    }

    fun addIncome(income: Income){
        incrementIncome(income)
        _transactions.add(income)
    }

    private fun incrementIncome(income: Income) {
        totalIncome += income.amount
    }

}

/**** Create DBPerson data class in database package
 * contains all fields in Person class except the transactions list will be splitted to 2 lists:
 * expenses (contains all expenses IDs) and incomes (contains all incomes IDs)
 * all fields will be declared in the constructor with val keyword
 * for example, see Invitation class and DBInvitation class
 *
 * create extension methods in both classes to convert from the original class to the DB class and vice versa
 * (see Invitation classes for more details)
 ****/