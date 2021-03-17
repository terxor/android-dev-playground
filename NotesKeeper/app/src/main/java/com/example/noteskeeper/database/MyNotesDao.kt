package com.example.noteskeeper.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MyNotesDao {
  @Insert
  suspend fun insert(x : MyNotes)

  @Query("SELECT *  from MyNotes_table")
  fun getAll() : LiveData<List<MyNotes>>

  @Query("DELETE from MyNotes_table")
  suspend fun clear()
}
