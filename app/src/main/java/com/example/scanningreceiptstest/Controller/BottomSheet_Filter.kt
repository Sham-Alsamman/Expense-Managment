package com.example.scanningreceiptstest.Controller

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.scanningreceiptstest.Model.GroupTransactionFilter
import com.example.scanningreceiptstest.Model.PeriodTransactionFilter
import com.example.scanningreceiptstest.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.bottomsheet_filter.*


class BottomSheet_Filter(val currentActivity: IFilterSheet) : BottomSheetDialogFragment() {

    var groupFilter: GroupTransactionFilter = GroupTransactionFilter.Individual
    var periodFilter: PeriodTransactionFilter = PeriodTransactionFilter.CurrentMonth

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

            dismiss()
            currentActivity.applyFilterChanges()

        }
    }
}
