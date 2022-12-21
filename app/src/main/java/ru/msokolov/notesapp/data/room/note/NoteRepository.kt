package ru.msokolov.notesapp.data.room.note

import androidx.lifecycle.LiveData
import javax.inject.Inject

class NoteRepository @Inject constructor(private val noteDao: NoteDao){
    suspend fun insert(noteEntity: NoteEntity) = noteDao.insert(noteEntity)

    suspend fun updateData(noteEntity: NoteEntity) = noteDao.update(noteEntity)

    suspend fun deleteItem(noteEntity: NoteEntity) = noteDao.delete(noteEntity)

    suspend fun deleteAll() {
        noteDao.deleteAll()
    }

    fun getAllNotes() = noteDao.getAllNotes()

    fun getAllPriorityNotes() = noteDao.getAllPriorityNotes()

    fun searchDatabase(searchQuery: String): LiveData<List<NoteEntity>> {
        return noteDao.searchDatabase(searchQuery)
    }
}