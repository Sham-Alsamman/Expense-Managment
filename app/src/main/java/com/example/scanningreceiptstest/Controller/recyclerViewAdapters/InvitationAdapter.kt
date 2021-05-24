package com.example.scanningreceiptstest.Controller.recyclerViewAdapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.scanningreceiptstest.Model.Invitation
import com.example.scanningreceiptstest.Model.InvitationStatus
import com.example.scanningreceiptstest.R
import kotlinx.android.synthetic.main.invitation_list_item.view.*

class InvitationAdapter(val clickListener: InvitationClickListener) :
    RecyclerView.Adapter<InvitationAdapter.InvitationViewHolder>() {

    var invitationsList = listOf<Invitation>()
        set(value) {
            field = value
            //tell the recycler view to redraw the list
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): InvitationViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(
            R.layout.invitation_list_item,
            parent,
            false
        )
        return InvitationViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: InvitationViewHolder,
        position: Int
    ) {
        val item = invitationsList[position]
        holder.bind(item, clickListener)
    }

    override fun getItemCount(): Int = invitationsList.size

    class InvitationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val invitationMsg: TextView = itemView.invitation_message
        private val joinButton: Button = itemView.joinBtn
        private val cancelButton: Button = itemView.cancelBtn

        fun bind(item: Invitation, clickListener: InvitationClickListener) {
            invitationMsg.text = item.message

            if (item.invitationStatus == InvitationStatus.NEW) {
                enableButtons(true)
            } else {
                enableButtons(false)
            }

            joinButton.setOnClickListener {
                clickListener.onJoinClick(item)
                enableButtons(false)
//                item.invitationStatus = InvitationStatus.REVIEWED
//                enableButtons(false)
//                Database.updateInvitation("+987654321"/**CURRENT_USER!!.phoneNumber*/, item.toDBInvitation())
//                /************accept invitation*************/
//                Log.i("invitation", item.id)
            }

            cancelButton.setOnClickListener {
                clickListener.onCancelClick(item)
                enableButtons(false)
//                item.invitationStatus = InvitationStatus.REVIEWED
//                enableButtons(false)
//                Database.updateInvitation("+987654321"/**CURRENT_USER!!.phoneNumber*/, item.toDBInvitation())
//                Log.i("invitation", "canceled")
            }
        }

        private fun enableButtons(enabled: Boolean) {
            joinButton.isEnabled = enabled
            cancelButton.isEnabled = enabled
        }
    }
}

class InvitationClickListener(
    val joinClickListener: (Invitation) -> Unit,
    val cancelClickListener: (Invitation) -> Unit
) {
    fun onJoinClick(invitation: Invitation) = joinClickListener(invitation)
    fun onCancelClick(invitation: Invitation) = cancelClickListener(invitation)
}