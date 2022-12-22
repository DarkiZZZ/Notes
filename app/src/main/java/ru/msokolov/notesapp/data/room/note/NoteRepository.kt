package ru.msokolov.notesapp.data.room.note

import androidx.lifecycle.LiveData
import javax.inject.Inject

class NoteRepository @Inject constructor(private val noteDao: NoteDao){

    suspend fun insertOrUpdate(noteEntity: NoteEntity) = noteDao.insertOrUpdate(noteEntity)

    suspend fun deleteNote(noteEntity: NoteEntity) = noteDao.delete(noteEntity)

    suspend fun deleteAll() {
        noteDao.deleteAllData()
    }

    fun getAllNotes() = noteDao.getAllNotes()

    fun getAllPriorityNotes() = noteDao.getAllPriorityNotes()

    fun searchDatabase(searchQuery: String): LiveData<List<NoteEntity>> {
        return noteDao.searchDatabase(searchQuery)
    }

    suspend fun getLastFetchedNote(): NoteEntity{
        return noteDao.getLastFetchedNote()
    }
}