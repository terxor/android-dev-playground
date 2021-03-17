package com.example.simplelogger.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SimpleLoggerViewModel : ViewModel() {
  // TODO: Implement the ViewModel

  /* done = Did the timer expire? */
  private var _done = MutableLiveData<Boolean>()
  val done: LiveData<Boolean>
    get() = _done

  /* Elapsed time in ms */
  private var _cur = MutableLiveData<Long>()
  val cur: LiveData<Long>
    get() = _cur

  private var lim = 0L
  private var baseTime = 0L

  fun initialize(t: Long) {
    _done.value = false
    _cur.value = 0L
    baseTime = System.currentTimeMillis()
    lim = baseTime + t
  }

  fun async_update() {
    val t = System.currentTimeMillis()
    if (t >= lim) _done.postValue(true)
    _cur.postValue(min(lim - baseTime, System.currentTimeMillis() - baseTime))
  }

  private fun min (a: Long, b: Long) : Long {
    return if (a < b) a else b
  }
}