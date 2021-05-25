package com.example.scanningreceiptstest

import android.app.NotificationManager
import android.content.Context
import android.util.Log
import androidx.core.content.getSystemService
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.scanningreceiptstest.Model.InvitationStatus
import com.example.scanningreceiptstest.database.DBInvitation
import com.example.scanningreceiptstest.database.FirebaseDatabase
import com.example.scanningreceiptstest.database.IDatabase
import com.example.scanningreceiptstest.database.toInvitationList

class InvitationWorker(appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {

    private val database: IDatabase = FirebaseDatabase

    companion object{
        //unique name to identify this worker
        const val WORK_NAME = "com.example.scanningreceiptstest.InvitationWorker"
    }

    override fun doWork(): Result {
        Log.i("worker", "triggered")
        createChannel(applicationContext)

        val user = SaveSharedPreference.getUserId(applicationContext)
        //if user is logged in get all invitations
        if (user != null){
            database.getAllInvitations(user, ::onDBInvitationResult)
        }

        return Result.success()
    }

    private fun onDBInvitationResult(list: List<DBInvitation>) {
        for (invitation in list.toInvitationList()) {
            //if the invitation is new, send a notification:
            if (invitation.invitationStatus == InvitationStatus.NEW) {
                //send notification
                val notificationManager = applicationContext.getSystemService<NotificationManager>()
                notificationManager?.sendNotification(invitation.message, applicationContext)
                return
            }
        }
    }
}