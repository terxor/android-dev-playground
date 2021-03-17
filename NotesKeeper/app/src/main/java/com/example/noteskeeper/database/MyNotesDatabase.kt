package com.example.noteskeeper.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
@Database(entities = [MyNotes::class], version = 1, exportSchema = false)
abstract class MyNotesDatabase : RoomDatabase(){
  abstract val myNotesDao:MyNotesDao
  companion object{
    @Volatile
    private var INSTANCE:MyNotesDatabase?=null
    fun instance(context: Context):MyNotesDatabase{
      synchronized(this){
        var instance= INSTANCE
        if(instance==null){
          instance= Room.databaseBuilder(
            context.applicationContext,
            MyNotesDatabase::class.java,
            "mynotes_database"
          )
            .fallbackToDestructiveMigration()
            .build()
          INSTANCE=instance
        }
        return instance
      }
    }
  }
}
