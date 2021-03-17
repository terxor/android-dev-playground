package com.example.noteskeeper.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.noteskeeper.database.MyNotes
import com.example.noteskeeper.database.MyNotesDao
import kotlinx.coroutines.launch

class NotesViewModel(
  private val database: MyNotesDao,
  application: Application
) : AndroidViewModel(application) {

  private val _notes = database.getAll()
  val notes: LiveData<List<MyNotes>>
    get() = _notes

  init{
    /* already initialized notes */
  }

  fun add(x : String) {
    Log.d("DBG", "add")
    viewModelScope.launch {
      database.insert(MyNotes(x = x))
    }
  }

  fun getAll(): List<String> {
    Log.d("DBG", "getAll")
    val l = mutableListOf<String>()
    for (e in _notes.value!!) {
      l.add(e.x)
    }
    return l
  }

  fun clearAll() {
    viewModelScope.launch {
      database.clear()
    }
  }
}
