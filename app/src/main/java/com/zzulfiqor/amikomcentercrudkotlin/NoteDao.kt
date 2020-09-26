package com.zzulfiqor.amikomcentercrudkotlin

import androidx.room.*

@Dao
interface NoteDao {
    @Insert
    fun insert(note: NoteModel)

    @Update
    fun update(note: NoteModel)

    @Delete
    fun delete(note: NoteModel)

    @Query("Select * from notes")
    fun getAll() : List<NoteModel>

    @Query("SELECT * FROM notes WHERE id = :id")
    fun getById(id: Int) : List<NoteModel>
}