package com.example.scanningreceiptstest.Controller

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import com.example.scanningreceiptstest.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.activity_add_manually.*
import kotlinx.android.synthetic.main.bottomsheet_filter.*

class BottomSheet_Filter : BottomSheetDialogFragment(){


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.bottomsheet_filter, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       // val spinner = findViewById<Spinner>(R.id.spin)

        // access the items of the list
        /*val spinItems = resources.getStringArray(R.array.TimePeriod)

        // access the spinner

        if (spinner != null) {
            val adapter = ArrayAdapter(this,
                android.R.layout.simple_list_item_1, spinItems)
            spinner.adapter = adapter*/

        spin.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                //

            }
            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
                spinner.setSelection(0)

            }
        }
        //}

        btn.setOnClickListener {
            Toast.makeText(context, " you", Toast.LENGTH_SHORT).show()

        }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

}