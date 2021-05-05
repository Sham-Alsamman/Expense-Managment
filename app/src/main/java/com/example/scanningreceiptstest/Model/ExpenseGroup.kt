package com.example.scanningreceiptstest.Model

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.scanningreceiptstest.database.*
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.*

class ExpenseGroup(val groupID: String, partners: MutableList<String>) {

    // here i want to declare list of person
    private val _partners: MutableList<String> = mutableListOf()
    val partners: List<String>
        get() = _partners

    var ID: String = "";


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

    companion object {
        //@RequiresApi(Build.VERSION_CODES.O)
        fun filterTransactions(
            list: List<Transaction>,
            periodFilter: PeriodTransactionFilter
        ): List<Transaction> { // should take two parameter transaction list to filter it and filter to filter list  depend on it
            val filteredList = mutableListOf<Transaction>()

            val now = Calendar.getInstance()
           //  filtering the transaction list depend on parameter

            var filterList = emptyList<Transaction>()

                when (periodFilter) {
                PeriodTransactionFilter.CurrentMonth -> {
                   val thisMonth = now.add(Calendar.MONTH,1)
                     filterList= list.filter { s -> s.date.month == now.time.month } as  MutableList<Transaction>
                }
                PeriodTransactionFilter.Last2Months -> {
                    val twoMonthsAgo= now.add(Calendar.MONTH,-2)
                     filterList=  list.filter { s -> s.date.month == now.time.month } as  MutableList<Transaction>
                }
                PeriodTransactionFilter.Last3Months -> {
                    val threeMonth= now.add(Calendar.MONTH,-1)
                     filterList=   list.filter { s -> s.date.month == now.time.month  } as  MutableList<Transaction>
                }
                PeriodTransactionFilter.Last4Months -> {
                    val fourMonth= now.add(Calendar.MONTH,-1)
                     filterList=   list.filter { s -> s.date.month == now.time.month  } as  MutableList<Transaction>
                }
                PeriodTransactionFilter.OneYear -> {
                    now.add(Calendar.YEAR, -1)
                     filterList=  list.filter { s -> s.date.year == now.get(Calendar.YEAR) } as  MutableList<Transaction>
                }
            }

            //Example to filter list :
            // var month: MutableList<String> = mutableListOf("January", "February", "March")
            // to get the result as list
            // var monthList: MutableList<String> = month.filter { s -> s == "January" } as MutableList<String>

            // return filter list

           // return list
            return filterList
        }
    }

}
