package com.example.simplelogger.util

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP
import androidx.core.app.NotificationCompat
import com.example.simplelogger.MainActivity
import com.example.simplelogger.R

private val NOTIFICATION_ID = 100

fun NotificationManager.sendNotification(messageBody: String, applicationContext: Context) {

  val contentIntent = Intent(applicationContext, MainActivity::class.java)
  val contentPendingIntent = PendingIntent.getActivity(
    applicationContext,
    NOTIFICATION_ID,
    contentIntent,
    PendingIntent.FLAG_UPDATE_CURRENT
  )
  val builder = NotificationCompat.Builder(
    applicationContext,
    applicationContext.getString(R.string.default_channel_id)
  )
    .setSmallIcon(R.drawable.ic_launcher_background)
    .setContentTitle(applicationContext.getString(R.string.default_title))
    .setContentText(messageBody)
    .setPriority(NotificationCompat.PRIORITY_LOW)
    .setContentIntent(contentPendingIntent)
    .setOngoing(true)
    .setOnlyAlertOnce(true)
  notify(NOTIFICATION_ID, builder.build())
}

