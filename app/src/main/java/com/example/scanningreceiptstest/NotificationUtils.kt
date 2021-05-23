package com.example.scanningreceiptstest

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.getSystemService
import com.example.scanningreceiptstest.Controller.Invitations
import java.util.*

private const val NOTIFICATION_CHANNEL_ID = "invitation_channel"
private const val NOTIFICATION_ID = 0

fun NotificationManager.sendNotification(messageBody: String, appContext: Context) {
    //intent to open Invitations page when notification clicked:
    val intent = Intent(appContext, Invitations::class.java)
    val pendingIntent = PendingIntent.getActivity(
        appContext,
        NOTIFICATION_ID,
        intent,
        PendingIntent.FLAG_ONE_SHOT
    )

    val builder = NotificationCompat.Builder(
        appContext,
        NOTIFICATION_CHANNEL_ID
    )
        .setSmallIcon(R.drawable.ic_wallet4)
        .setContentTitle("New Invitation")
        .setStyle(NotificationCompat.BigTextStyle().bigText(messageBody))
        .setContentIntent(pendingIntent)
        .setAutoCancel(true)
        .setPriority(NotificationCompat.PRIORITY_HIGH)

    notify(NOTIFICATION_ID, builder.build())
}

// the name the user see in the sittings
fun createChannel(
    context: Context,
    channelId: String = NOTIFICATION_CHANNEL_ID,
    channelName: String = "New invitations"
) {
    //create a channel if the API level >= 26
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val notificationChannel = NotificationChannel(
            channelId, channelName, NotificationManager.IMPORTANCE_HIGH
        )

        //some configurations:
        notificationChannel.enableLights(true)
        notificationChannel.lightColor = Color.RED
        notificationChannel.enableVibration(true)
        notificationChannel.description = "New Invitation"
        notificationChannel.setShowBadge(false)

        //create the notification channel using the notification manager
        val notificationManager = context.getSystemService<NotificationManager>()
        notificationManager?.createNotificationChannel(notificationChannel)
    }
}