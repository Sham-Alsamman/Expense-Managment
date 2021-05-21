package com.example.scanningreceiptstest.Controller.recyclerViewAdapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.scanningreceiptstest.Model.Expense
import com.example.scanningreceiptstest.Model.Income
import com.example.scanningreceiptstest.Model.Transaction
import com.example.scanningreceiptstest.R
import kotlinx.android.synthetic.main.transaction_history_list_item.view.*
import java.text.SimpleDateFormat
import java.util.*

class TransactionHistoryAdapter(private val layoutID: Int) :
    RecyclerView.Adapter<TransactionHistoryAdapter.TransactionViewHolder>() {

    var transactionsList = listOf<Transaction>()
        set(value) {
            Collections.sort(value){ o1, o2 ->
                return@sort o2.date.compareTo(o1.date)
            }
            field = value
            //tell the recycler view to redraw the list
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(
            layoutID,
            parent,
            false
        )
        return TransactionViewHolder(view)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val item = transactionsList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        if (layoutID == R.layout.transation_history_home_item && transactionsList.size > 10)
            return 10

        return transactionsList.size
    }

    class TransactionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val name: TextView = itemView.transactionNameTv
        private val date: TextView = itemView.transactionDateTv
        private val amount: TextView = itemView.transactionAmountTv

        fun bind(item: Transaction) {
            val formatter = SimpleDateFormat("dd/MM/yyyy")
            date.text = formatter.format(item.date)
            if (item is Income) {
                amount.text = "+${item.amount}JD"
                amount.setTextColor(Color.parseColor("#7ED321"))
                name.text = item.name
            } else if (item is Expense) {
                amount.text = "-${item.amount}JD"
                amount.setTextColor(Color.parseColor("#D32121"))
                name.text = item.category + " / " + item.vendorName
            }
        }
    }
}

