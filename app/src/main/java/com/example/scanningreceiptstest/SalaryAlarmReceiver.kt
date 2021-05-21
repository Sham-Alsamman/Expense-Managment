package com.example.scanningreceiptstest

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.SystemClock
import android.text.format.DateUtils
import android.util.Log
import androidx.core.content.getSystemService
import com.example.scanningreceiptstest.Model.Income
import com.example.scanningreceiptstest.database.CURRENT_USER
import com.example.scanningreceiptstest.database.Database
import java.time.LocalDate
import java.util.*

class SalaryAlarmReceiver : BroadcastReceiver() {

    companion object {
        const val SALARY_ALARM_REQUEST_CODE = 0
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        if (CURRENT_USER != null) {
            Log.i("Alarm", "triggered")

            //save the remaining from the last month in the saving wallet:
            CURRENT_USER!!.atEndOfMonth()

            //add the new salary:
            val salary = Income(Date(), CURRENT_USER!!.monthlySalary, "Monthly salary")
            Database.addNewIncome(CURRENT_USER!!.phoneNumber, salary.toDBIncome())
            CURRENT_USER!!.addSalaryAndCalculateSaving()

            Database.updateUserInfo(CURRENT_USER!!.toDBPerson())

            if (context != null) {
                setSalaryAlarmIfNotExist(context)
            }
        }
    }
}

fun setSalaryAlarmIfNotExist(context: Context) {
    Log.i("Alarm", "setSalaryAlarmIfNotExist called")

//    // check if alarm already set:
//    val alarmSet = PendingIntent.getBroadcast(
//        context,
//        SalaryAlarmReceiver.SALARY_ALARM_REQUEST_CODE,
//        Intent(context, SalaryAlarmReceiver::class.java),
//        PendingIntent.FLAG_NO_CREATE
//    ) != null
//
//    if (alarmSet) return
//
//    Log.i("Alarm", "creating new alarm...")

    // set new alarm:
    //val interval = DateUtils.MINUTE_IN_MILLIS//DateUtils.WEEK_IN_MILLIS * 7

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
    //val triggerTime = SystemClock.elapsedRealtime() + interval

    Log.i("Alarm", "setting alarm to ${calendar.get(Calendar.MONTH) + 1}, ${calendar.get(Calendar.DAY_OF_MONTH)}")

    val pendingIntent = PendingIntent.getBroadcast(
        context,
        SalaryAlarmReceiver.SALARY_ALARM_REQUEST_CODE,
        Intent(context, SalaryAlarmReceiver::class.java),
        PendingIntent.FLAG_UPDATE_CURRENT
    )

//    alarmManager?.setRepeating(
//        AlarmManager.ELAPSED_REALTIME_WAKEUP,
//        triggerTime,
//        interval,
//        pendingIntent
//    )

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