package com.example.scanningreceiptstest.Controller

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.scanningreceiptstest.Model.GroupTransactionFilter
import com.example.scanningreceiptstest.Model.PeriodTransactionFilter
import com.example.scanningreceiptstest.Model.Person
import com.example.scanningreceiptstest.Model.Transaction
import com.example.scanningreceiptstest.R
import com.example.scanningreceiptstest.database.CURRENT_GROUP
import com.example.scanningreceiptstest.database.CURRENT_USER
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.bottomsheet_filter.*
import java.util.*
import kotlin.collections.ArrayList


class BottomSheet_Filter(val currentActivity: IFilterSheet) : BottomSheetDialogFragment() {

    var groupFilter: GroupTransactionFilter = GroupTransactionFilter.Individual
    var periodFilter: PeriodTransactionFilter = PeriodTransactionFilter.CurrentMonth
    //var selectedTransactions: ArrayList<Transaction> = ArrayList()
    //var filteredTransactions: ArrayList<Transaction> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.bottomsheet_filter, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn.setOnClickListener {
            if (group.isChecked) {
                groupFilter = GroupTransactionFilter.Group
            } else if (individual.isChecked) {
                groupFilter = GroupTransactionFilter.Individual
            }

            when (spin.selectedItemPosition) {
                0 -> periodFilter = PeriodTransactionFilter.CurrentMonth
                1 -> periodFilter = PeriodTransactionFilter.Last2Months
                2 -> periodFilter = PeriodTransactionFilter.Last3Months
                3 -> periodFilter = PeriodTransactionFilter.Last4Months
                4 -> periodFilter = PeriodTransactionFilter.OneYear
            }

            Toast.makeText(context, "$groupFilter & $periodFilter", Toast.LENGTH_SHORT).show()
/*
            if (groupFilter == GroupTransactionFilter.Group) {
                var groupID = CURRENT_USER!!.groupId

                var group: List<String> = CURRENT_GROUP!!.partners

                // iterate all users having the same group id

                for (id in group) {
                    // var user : Person = get the user from DB by ID
                    //filterByTime(user, periodFilter)
                }
            } else if (groupFilter == GroupTransactionFilter.Individual) {
                filterByTime(CURRENT_USER!!, periodFilter)
            }

            Report.get_Filtered_transactions(filteredTransactions)
*/
            dismiss()
            currentActivity.applyFilterChanges()

        }
    }

}
