package com.example.noteskeeper.ui

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.noteskeeper.database.MyNotesDao

class NotesViewModelFactory (
  private val dataSource: MyNotesDao,
  private val application: Application) : ViewModelProvider.Factory {
  @Suppress("unchecked_cast")
  override fun <T : ViewModel?> create(modelClass: Class<T>): T {
    if (modelClass.isAssignableFrom(NotesViewModel::class.java)) {
      return NotesViewModel(dataSource, application) as T
    }
    throw IllegalArgumentException("Unknown ViewModel class")
  }
}
