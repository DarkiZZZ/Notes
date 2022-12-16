package ru.msokolov.notesapp.model.notes.repository

import androidx.lifecycle.LiveData
import ru.msokolov.notesapp.model.notes.NotesDao
import ru.msokolov.notesapp.model.notes.entities.NoteDbEntity

class RoomNotesRepository(
    private val notesDao: NotesDao
) : NotesRepository {

    override suspend fun createNewNote(noteDbEntity: NoteDbEntity) {
        notesDao.create(noteDbEntity)
    }

    override suspend fun updateNote(noteDbEntity: NoteDbEntity) {
        notesDao.update(noteDbEntity)
    }

    override suspend fun deleteNote(noteDbEntity: NoteDbEntity) {
        notesDao.delete(noteDbEntity)
    }

    override fun findById(noteId: Long): LiveData<NoteDbEntity> {
        return notesDao.findById(noteId)
    }

    override fun getAllNotes(): LiveData<List<NoteDbEntity>> {
        return notesDao.getAllNotes()
    }

}