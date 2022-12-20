package ru.msokolov.notesapp.data

import androidx.lifecycle.LiveData
import ru.msokolov.notesapp.data.room.NoteDao
import ru.msokolov.notesapp.data.room.NoteEntity
import javax.inject.Inject

class NoteRepository @Inject constructor(private val noteDao: NoteDao){
    suspend fun insert(noteEntity: NoteEntity) = noteDao.insert(noteEntity)

    suspend fun updateData(noteEntity: NoteEntity) = noteDao.update(noteEntity)

    suspend fun deleteItem(noteEntity: NoteEntity) = noteDao.delete(noteEntity)

    suspend fun deleteAll() {
        noteDao.deleteAll()
    }

    fun getAllTasks() = noteDao.getAllTasks()

    fun getAllPriorityTasks() = noteDao.getAllPriorityTasks()

    fun searchDatabase(searchQuery: String): LiveData<List<NoteEntity>> {
        return noteDao.searchDatabase(searchQuery)
    }
}