package com.shabeer.notesroomdb.room.presantation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shabeer.notesroomdb.room.data.models.NotesEntity
import com.shabeer.notesroomdb.room.data.repositorys.NotesRepository
import com.shabeer.notesroomdb.room.roomdb.NotesDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(private val notesRepository: NotesRepository, private val dao: NotesDao) : ViewModel() {

    private val getAllNotes = MutableLiveData<List<NotesEntity>>()
    val _getAllNotes: LiveData<List<NotesEntity>> = getAllNotes

    init {
        getAllNotesFromRoomDb()
    }

    fun insertNotes(notesEntity: NotesEntity){
        viewModelScope.launch(Dispatchers.IO) {
            dao.insertNotes(notesEntity)
            getAllNotesFromRoomDb()
        }
    }

    fun deleteNote(notesEntity: NotesEntity) {
        viewModelScope.launch {
            dao.deleteNoteById(notesEntity.id)
            getAllNotesFromRoomDb()
        }
    }

    fun getAllNotesFromRoomDb() {
        viewModelScope.launch {
            val responce = notesRepository.getAllNotes()
            getAllNotes.value = responce
        }
    }
}