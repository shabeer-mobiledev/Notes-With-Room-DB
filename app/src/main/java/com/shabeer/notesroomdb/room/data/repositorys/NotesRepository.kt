package com.shabeer.notesroomdb.room.data.repositorys

import com.shabeer.notesroomdb.room.roomdb.NotesDao
import javax.inject.Inject

class NotesRepository @Inject constructor(private val notesDao: NotesDao) {
    suspend fun getAllNotes() = notesDao.getALlNotes()
}