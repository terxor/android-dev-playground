package com.example.simplelogger.ui

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
import androidx.lifecycle.ViewModelProvider
import com.example.simplelogger.R
import com.example.simplelogger.util.Updater
import java.util.*

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

        viewModel = ViewModelProvider(this).get(SimpleLoggerViewModel::class.java)

        button.setOnClickListener {
            val t = textField.text.toString().toLongOrNull()
            Log.d("DBG", t.toString())
            if (t == null) Toast.makeText(context, "Enter time in ms", Toast.LENGTH_SHORT).show()
            else {
                go(t)
            }
        }
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
    }

    private fun go(t: Long) {
        viewModel.cur.removeObservers(viewLifecycleOwner)
        viewModel.done.removeObservers(viewLifecycleOwner)

        button.isEnabled = false
        textField.isEnabled = false

        val updater = Updater(viewModel)
        viewModel.initialize(t)
        val timer = Timer()
        timer.schedule(updater, 0L, 1L)
        viewModel.cur.observe(viewLifecycleOwner, { count -> textView.text = count.toString() })
        viewModel.done.observe(viewLifecycleOwner, { done -> if (done) {
            Log.d("DBG", "Observed " + observerCount++)
            timer.cancel()
            Toast.makeText(context, "DONE", Toast.LENGTH_SHORT).show()
            button.isEnabled = true
            textField.isEnabled = true
            textField.setText("")
        } })
    }

}