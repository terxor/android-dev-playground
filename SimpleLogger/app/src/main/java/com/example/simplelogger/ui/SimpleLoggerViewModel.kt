package com.example.simplelogger.ui

import android.app.Application
import android.app.NotificationManager
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.simplelogger.BuildConfig
import com.example.simplelogger.util.sendNotification
import java.util.*

class SimpleLoggerViewModel(private val app: Application) : AndroidViewModel(app) {
  // TODO: Implement the ViewModel

  private var _isTimerRunning = MutableLiveData<Boolean>(false)
  val isTimerRunning: LiveData<Boolean>
    get() = _isTimerRunning

  /* Elapsed time in ms */
  private var _cur = MutableLiveData<Long>()
  val cur: LiveData<Long>
    get() = _cur

  private var lim = 0L
  private var baseTime = 0L

  private lateinit var timer : Timer

  fun initialize(t: Long) {

    /* Timer should not be running */
    if (BuildConfig.DEBUG && isTimerRunning.value != false) {
      error("Assertion failed")
    }

    _isTimerRunning.value = true
    _cur.value = 0L

    baseTime = System.currentTimeMillis()
    lim = t

    timer = Timer()
    timer.schedule(
      object : TimerTask() {
        override fun run() {
          Log.d("DBG", "hi")
          async_update()
        }
      }, 0L, 500L
    )
  }

  private fun finish() {
    _isTimerRunning.postValue(false)
    lim = 0L
    timer.cancel()

    val notificationManager = ContextCompat.getSystemService(
      app,
      NotificationManager::class.java
    ) as NotificationManager
    notificationManager.cancelAll()
  }

  fun async_update() {
    val t = System.currentTimeMillis() - baseTime
    val z = min(lim, t)
    _cur.postValue(z)

    val progress = (100.0 * z) / lim
    val notificationManager = ContextCompat.getSystemService(
      app,
      NotificationManager::class.java
    ) as NotificationManager
    notificationManager.sendNotification("Current: $progress", app)
    if (t >= lim) finish()
  }

  private fun min (a: Long, b: Long) : Long {
    return if (a < b) a else b
  }
}