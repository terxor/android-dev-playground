package com.example.noteskeeper.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="MyNotes_table")
data class MyNotes(
  @PrimaryKey(autoGenerate = true)
  var id: Long = 0L,
  @ColumnInfo(name = "note")
  var x:String
)
