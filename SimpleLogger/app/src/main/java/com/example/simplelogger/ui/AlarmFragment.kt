package com.example.simplelogger.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.simplelogger.R

class AlarmFragment : Fragment() {

  companion object {
    fun newInstance() = AlarmFragment()
  }

  private lateinit var viewModel: AlarmViewModel
  private lateinit var buttonSetAlarm: Button
  private lateinit var editText: EditText

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    val root = inflater.inflate(R.layout.alarm_fragment, container, false)
    buttonSetAlarm = root.findViewById(R.id.buttonSetAlarm)
    editText = root.findViewById(R.id.editTextTextPersonName2)
    root.findViewById<Button>(R.id.buttonToLogger).setOnClickListener {
      findNavController().navigate(R.id.action_alarmFragment_to_simpleLogger)
    }

    buttonSetAlarm.setOnClickListener {
      val t = editText.text.toString().toLongOrNull()
      if (t == null) Toast.makeText(context, "Enter time in ms", Toast.LENGTH_SHORT).show()
      else viewModel.setAlarm(t)
    }
    return root
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    viewModel = ViewModelProvider(this).get(AlarmViewModel::class.java)
    // TODO: Use the ViewModel
  }

}