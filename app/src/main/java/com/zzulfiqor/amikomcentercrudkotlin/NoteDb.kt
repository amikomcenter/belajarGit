package com.zzulfiqor.amikomcentercrudkotlin

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized

@Database(entities = [NoteModel::class], version = 1, exportSchema = false)
abstract class NoteDb : RoomDatabase() {
    @InternalCoroutinesApi
    companion object{
        @Volatile
        private var INSTANCE: NoteDb? = null

        fun getDbNotes(context: Context): NoteDb{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext, NoteDb::class.java, "Note_DB"
                )
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    abstract fun getNoteDao(): NoteDao
}