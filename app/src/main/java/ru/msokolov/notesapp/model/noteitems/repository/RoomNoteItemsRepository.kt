package ru.msokolov.notesapp.model.noteitems.repository

import androidx.lifecycle.LiveData
import ru.msokolov.notesapp.model.noteitems.NoteItemsDao
import ru.msokolov.notesapp.model.noteitems.entities.NoteItemDbEntity

class RoomNoteItemsRepository(
    private val noteItemsDao: NoteItemsDao
) : NoteItemsRepository{

    override suspend fun createNewNoteItem(noteItemDbEntity: NoteItemDbEntity) {
        noteItemsDao.create(noteItemDbEntity)
    }

    override suspend fun updateNoteItem(noteItemDbEntity: NoteItemDbEntity) {
        noteItemsDao.update(noteItemDbEntity)
    }

    override suspend fun deleteNoteItem(noteItemDbEntity: NoteItemDbEntity) {
        noteItemsDao.delete(noteItemDbEntity)
    }

    override suspend fun findById(noteItemId: Long): LiveData<NoteItemDbEntity> {
        return noteItemsDao.findById(noteItemId)
    }

    override fun getAllNoteItems(): LiveData<List<NoteItemDbEntity>> {
        return noteItemsDao.getAllNoteItems()
    }
}