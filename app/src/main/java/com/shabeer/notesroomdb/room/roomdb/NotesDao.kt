package com.shabeer.notesroomdb.room.roomdb

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.shabeer.notesroomdb.room.data.models.NotesEntity

@Dao
interface NotesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNotes(notesEntity: NotesEntity)

    @Delete()
    suspend fun deleteNotes(notesEntity: NotesEntity)

    @Update()
    suspend fun updateNotes(notesEntity: NotesEntity)

    @Query("SELECT * FROM notes_table ORDER BY date DESC")
    suspend fun getALlNotes() : List<NotesEntity>


    @Query("DELETE FROM notes_table WHERE id = :noteId")
    suspend fun deleteNoteById(noteId: Long)

}