package com.example.scanningreceiptstest

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.content.getSystemService
import com.example.scanningreceiptstest.Model.Income
import com.example.scanningreceiptstest.database.CURRENT_USER
import com.example.scanningreceiptstest.database.FirebaseDatabase
import com.example.scanningreceiptstest.database.IDatabase
import java.util.*

class SalaryAlarmReceiver : BroadcastReceiver() {

    private val database: IDatabase = FirebaseDatabase

    companion object {
        const val SALARY_ALARM_REQUEST_CODE = 0
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        if (CURRENT_USER != null) {
            Log.i("Alarm", "triggered")

            //add the new salary to DB:
            val salary = Income(Date(), CURRENT_USER!!.monthlySalary, "Monthly salary")
            database.addNewIncome(CURRENT_USER!!.phoneNumber, salary.toDBIncome())
            //save the remaining from the last month in the saving wallet and add the new salary:
            CURRENT_USER!!.addSalaryAndCalculateSaving()

            database.updateUserInfo(CURRENT_USER!!.toDBPerson())

            if (context != null) {
                setSalaryAlarm(context)
            }
        }
    }
}

fun setSalaryAlarm(context: Context) {
    Log.i("Alarm", "setSalaryAlarm called")

    val calendar = Calendar.getInstance().apply {
        timeInMillis = System.currentTimeMillis()
        set(Calendar.MILLISECOND, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.HOUR_OF_DAY, 8)
        set(Calendar.DAY_OF_MONTH, 1)
    }
    val currentDate = Calendar.getInstance()
    if (currentDate.after(calendar)) {
        calendar.add(Calendar.MONTH, 1)
    }

    val alarmManager = context.getSystemService<AlarmManager>()

    Log.i("Alarm", "setting alarm to ${calendar.get(Calendar.MONTH) + 1}, ${calendar.get(Calendar.DAY_OF_MONTH)}")

    val pendingIntent = PendingIntent.getBroadcast(
        context,
        SalaryAlarmReceiver.SALARY_ALARM_REQUEST_CODE,
        Intent(context, SalaryAlarmReceiver::class.java),
        PendingIntent.FLAG_UPDATE_CURRENT
    )

    alarmManager?.setExact(
        AlarmManager.RTC_WAKEUP,
        calendar.timeInMillis,
        pendingIntent
    )
}

fun cancelSalaryAlarm(context: Context) {
    Log.i("Alarm", "cancelSalaryAlarm called")

    // check if alarm already set:
    val alarmPending = PendingIntent.getBroadcast(
        context,
        SalaryAlarmReceiver.SALARY_ALARM_REQUEST_CODE,
        Intent(context, SalaryAlarmReceiver::class.java),
        PendingIntent.FLAG_NO_CREATE
    )

    if (alarmPending == null) return

    val alarmManager = context.getSystemService<AlarmManager>()
    alarmManager?.cancel(alarmPending)
}