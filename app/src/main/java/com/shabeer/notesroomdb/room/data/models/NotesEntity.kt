package com.shabeer.notesroomdb.room.data.models

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey

@Keep
@Entity(tableName = "notes_table")
data class NotesEntity(
    @PrimaryKey(autoGenerate = true)
    val id : Long = 0,
    val title : String,
    val discription : String,
    val date: Long = System.currentTimeMillis()
)
