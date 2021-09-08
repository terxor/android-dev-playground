package com.example.simplelogger.ui

import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.example.simplelogger.util.AlarmReceiver


class  AlarmViewModel(val app: Application) : AndroidViewModel(app) {
  // TODO: Implement the ViewModel
  private val NOTIFICATION_ID = 0

  fun setAlarm(t: Long) {
    Log.d("DBG", "Setting alarm for $t ms")

    val contentIntent = Intent(app.applicationContext, AlarmReceiver::class.java)
    val contentPendingIntent = PendingIntent.getBroadcast(
      app.applicationContext,
      NOTIFICATION_ID,
      contentIntent,
      PendingIntent.FLAG_UPDATE_CURRENT
    )
    val alarmManager = app.getSystemService(ALARM_SERVICE) as AlarmManager
    var triggerTime = System.currentTimeMillis() + t
    Log.d("DBG", "alarm for $triggerTime")

    alarmManager.setWindow(AlarmManager.RTC_WAKEUP, triggerTime,1000L, contentPendingIntent)

    Log.d("DBG", "after ${System.currentTimeMillis()}")
  }

}