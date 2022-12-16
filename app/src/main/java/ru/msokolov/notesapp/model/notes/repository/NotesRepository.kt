package ru.msokolov.notesapp.model.notes.repository

import androidx.lifecycle.LiveData
import ru.msokolov.notesapp.model.notes.entities.NoteDbEntity

interface NotesRepository {

    suspend fun createNewNote(noteDbEntity: NoteDbEntity)

    suspend fun updateNote(noteDbEntity: NoteDbEntity)

    suspend fun deleteNote(noteDbEntity: NoteDbEntity)

    fun findById(noteId: Long): LiveData<NoteDbEntity>

    fun getAllNotes(): LiveData<List<NoteDbEntity>>
}