package com.shabeer.notesroomdb.room.roomdb

import androidx.room.Database
import androidx.room.RoomDatabase
import com.shabeer.notesroomdb.room.data.models.NotesEntity

@Database(entities = [NotesEntity::class], version = 1, exportSchema = true)
abstract class NotesDatabase : RoomDatabase() {

    abstract fun getNotesDau(): NotesDao

}