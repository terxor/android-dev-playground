package com.example.simplelogger.ui

import android.app.NotificationChannel
import android.app.NotificationManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.simplelogger.R

class SimpleLogger : Fragment() {

  companion object {
    fun newInstance() = SimpleLogger()
  }

  private lateinit var viewModel: SimpleLoggerViewModel

  private var observerCount = 0
  private lateinit var button: Button
  private lateinit var textView: TextView
  private lateinit var textField: EditText

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    val root = inflater.inflate(R.layout.simple_logger_fragment, container, false)

    /* UI Components */
    textView = root.findViewById(R.id.textView)
    textField = root.findViewById<EditText>(R.id.editTextTextPersonName)
    button = root.findViewById<Button>(R.id.button)
    root.findViewById<Button>(R.id.buttonToAlarm).setOnClickListener {
      findNavController().navigate(R.id.action_simpleLogger_to_alarmFragment)
    }

    viewModel = ViewModelProvider(this).get(SimpleLoggerViewModel::class.java)

    val timerRunning = (viewModel.isTimerRunning.value == true)
    button.isEnabled = !timerRunning
    textField.isEnabled = !timerRunning

    button.setOnClickListener {
      val t = textField.text.toString().toLongOrNull()
      Log.d("DBG", t.toString())
      if (t == null) Toast.makeText(context, "Enter time in ms", Toast.LENGTH_SHORT).show()
      else viewModel.initialize(t)
    }

    viewModel.cur.removeObservers(viewLifecycleOwner)
    viewModel.isTimerRunning.removeObservers(viewLifecycleOwner)

    viewModel.cur.observe(viewLifecycleOwner,
      Observer<Long> { count -> textView.text = count.toString() })
    viewModel.isTimerRunning.observe(viewLifecycleOwner, Observer<Boolean> { timerRunning ->
      run {
        if (!timerRunning) Toast.makeText(context, "DONE", Toast.LENGTH_SHORT).show()
        textField.setText("")
        button.isEnabled = !timerRunning
        textField.isEnabled = !timerRunning
      }
    })

    createChannel(getString(R.string.default_channel_id), "My Channel")

    return root
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    // TODO: Use the ViewModel
  }


  private fun createChannel(channelId: String, channelName: String) {
    // TODO: Step 1.6 START create a channel
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      val notificationChannel = NotificationChannel(
        channelId,
        channelName,
        // TODO: Step 2.4 change importance
        NotificationManager.IMPORTANCE_HIGH
      )
      // TODO: Step 2.6 disable badges for this channel

      notificationChannel.enableLights(true)
      notificationChannel.lightColor = Color.RED
      notificationChannel.enableVibration(true)
      notificationChannel.description = "Time for breakfast"

      val notificationManager = requireActivity().getSystemService(
        NotificationManager::class.java
      )
      notificationManager.createNotificationChannel(notificationChannel)
    }
    // TODO: Step 1.6 END create channel
  }
}