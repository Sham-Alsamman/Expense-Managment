package com.example.scanningreceiptstest

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class InvitationWorker(appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {

    companion object{
        //unique name to identify this worker
        const val WORK_NAME = "com.example.scanningreceiptstest.InvitationWorker"
    }

    override fun doWork(): Result {
        Log.i("worker", "triggered")
        return Result.success()
    }
}