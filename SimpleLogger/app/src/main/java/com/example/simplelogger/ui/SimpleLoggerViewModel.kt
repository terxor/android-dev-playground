package com.example.simplelogger.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*

class SimpleLoggerViewModel : ViewModel() {
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
    assert(isTimerRunning.value == false)
    _isTimerRunning.value = true
    _cur.value = 0L

    baseTime = System.currentTimeMillis()
    lim = t

    timer = Timer()
    timer.schedule(object : TimerTask() {
      override fun run() {
        Log.d("DBG", "hi")
        async_update()
      } }, 0L, 1L
    )
  }

  private fun finish() {
    _isTimerRunning.postValue(false)
    lim = 0L
    timer.cancel()
  }

  fun async_update() {
    val t = System.currentTimeMillis() - baseTime
    _cur.postValue(min(lim, t))
    if (t >= lim) finish()
  }

  private fun min (a: Long, b: Long) : Long {
    return if (a < b) a else b
  }
}