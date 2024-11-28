package com.shabeer.notesroomdb.room.di

import android.content.Context
import androidx.room.Room
import com.shabeer.notesroomdb.room.data.repositorys.NotesRepository
import com.shabeer.notesroomdb.room.roomdb.NotesDao
import com.shabeer.notesroomdb.room.roomdb.NotesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class Module {


    @Provides
    @Singleton
    fun provideNotesDatabase(@ApplicationContext context: Context): NotesDatabase {
        return Room.databaseBuilder(
                context.applicationContext,
                NotesDatabase::class.java,
                "notes_database"
            ).build()
    }

    @Provides
    fun provideNotesDao(notesDatabase: NotesDatabase) : NotesDao{
        return notesDatabase.getNotesDau()
    }

    @Provides
    fun provideRepository(notesDao: NotesDao) : NotesRepository {
        return NotesRepository(notesDao)
    }
}