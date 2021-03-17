package com.example.noteskeeper.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.noteskeeper.R
import com.example.noteskeeper.database.MyNotes
import com.example.noteskeeper.database.MyNotesDatabase

class Notes : Fragment() {

  companion object {
    fun newInstance() = Notes()
  }

  private lateinit var viewModel: NotesViewModel
  private lateinit var linearLayout : LinearLayout
  private lateinit var textfield: EditText
  private lateinit var button : Button

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    val root = inflater.inflate(R.layout.notes_fragment, container, false)
    linearLayout=root.findViewById(R.id.linearLayout)
    button=root.findViewById(R.id.button)
    textfield=root.findViewById(R.id.editTextTextPersonName)

    val application = requireNotNull(this.activity).application
    val dataSource = MyNotesDatabase.instance(application).myNotesDao
    val viewModelFactory = NotesViewModelFactory(dataSource, application)
    viewModel = ViewModelProvider(this, viewModelFactory).get(NotesViewModel::class.java)
    viewModel.notes.observe(viewLifecycleOwner, { list -> refreshList(list) })
    return root
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    // TODO: Use the ViewModel
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    button.setOnClickListener(){
      viewModel.add(textfield.text.toString())
    }
  }

  private fun refreshList(list: List<MyNotes>) {
    linearLayout.removeAllViews()
    if (list != null) {
      for(i in list) {
        val tmp = TextView(activity)
        tmp.text = i.x
        linearLayout.addView(tmp)
      }
    }
  }

}