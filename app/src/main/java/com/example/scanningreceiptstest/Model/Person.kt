package com.example.scanningreceiptstest.Model

import com.example.scanningreceiptstest.database.DBPerson
import java.io.Serializable;


class Person(userName: String, phoneNum: String,Password: String) :Serializable {

    constructor(
        phoneNum: String,
        userName: String,
        Password: String,
        groupId: String,
        monthlySal: Double,
        totalIncome: Double,
        savingAmount: Double,
        savingWallet: Double,
        transactions: List<Transaction>
    )
            : this(userName, phoneNum,Password) {
        this.groupId = groupId
        this.monthlySalary = monthlySal
        this.totalIncome = totalIncome
        this.savingAmount = savingAmount
        this.savingWallet = savingWallet
        this._transactions = transactions as MutableList<Transaction>
    }

    var name: String = userName
    var password: String = Password

    // the phone number is the ID of person
    var phoneNumber: String = phoneNum

    var groupId: String = ""

    var monthlySalary: Double = 0.0
        set(value) { //instead of setMonthlySalary() method
            if (value >= 0)
                field = value
        }

    var totalIncome: Double = 0.0 // should be always positive num??
    var savingAmount: Double = 0.0
        set(value) { // instead of setSavingAmount() method
            if (value >= 0)
                field = value
        }

    var savingWallet: Double = 0.0
        set(value) {
            if (value >= 0)
                field = value
        }
    private var _transactions = mutableListOf<Transaction>()

    /**instead of getTransactions method**/
    val transactions: List<Transaction>
        get() = _transactions


    //not here?
    fun changeGroup(newGroupId: String) {
        groupId = newGroupId
    }

    //not here?
    fun receiveInvite(homeId: String) {

    }
   /*
    fun CheckPassword(Phone :String ,Pass:String){

        var p: Person?=null
        var passval = Pass.toString().trim()
        Database.userRef.orderByChild("phoneNumber").equalTo(Phone).addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (postSnapshot in snapshot.children) {
                        p= postSnapshot.getValue(Person::class.java)

                    }
                    val l = p?.password.toString()
                    val result = BCrypt.verifyer().verify(passval.toCharArray(), l)
                    if (result.verified) {
                        // correct password
                    } else {
                        //not correct password
                    }
                }
                else{
                    // phone number not exist
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    */
    fun addExpense(expense: Expense) {
        _transactions.add(expense)
        /****edit total***/
    }

    fun addIncome(income: Income) {
        incrementIncome(income)
        _transactions.add(income)
    }

    private fun incrementIncome(income: Income) {
        totalIncome += income.amount
    }



    fun toDBPerson(): DBPerson {
        return DBPerson(
            phoneNumber,
            name,
            password,
            groupId,
            monthlySalary,
            totalIncome,
            savingAmount,
            savingWallet,
        )
    }

}