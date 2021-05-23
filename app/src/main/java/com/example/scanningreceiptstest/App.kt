package com.example.scanningreceiptstest

import android.app.Application
import android.util.Log
import androidx.work.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class App : Application() {

    private val appScope = CoroutineScope(Dispatchers.Default)

    override fun onCreate() {
        super.onCreate()
        Log.i("worker", "App onCreate called")
        setUpInvitationWorker()
    }

    private fun setUpInvitationWorker() = appScope.launch {
        Log.i("worker", "setUpInvitationWorker called")

        //if there is logged-in user schedule the work:
        val user = SaveSharedPreference.getUserId(this@App)
        if (user != null) {

            val constraint = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .setRequiresBatteryNotLow(true)
                .build()

            val repeatingRequest = PeriodicWorkRequestBuilder<InvitationWorker>(5, TimeUnit.SECONDS)
                .setConstraints(constraint)
                .build()

            WorkManager.getInstance(this@App).enqueueUniquePeriodicWork(
                InvitationWorker.WORK_NAME,
                ExistingPeriodicWorkPolicy.KEEP,
                repeatingRequest
            )
        }
    }
}