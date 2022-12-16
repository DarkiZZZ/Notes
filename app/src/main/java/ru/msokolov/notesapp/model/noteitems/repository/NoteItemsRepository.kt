package ru.msokolov.notesapp.model.noteitems.repository

import androidx.lifecycle.LiveData
import ru.msokolov.notesapp.model.noteitems.entities.NoteItemDbEntity
interface NoteItemsRepository {

    suspend fun createNewNoteItem(noteItemDbEntity: NoteItemDbEntity)

    suspend fun updateNoteItem(noteItemDbEntity: NoteItemDbEntity)

    suspend fun deleteNoteItem(noteItemDbEntity: NoteItemDbEntity)

    suspend fun findById(noteItemId: Long): LiveData<NoteItemDbEntity>

    fun getAllNoteItems(): LiveData<List<NoteItemDbEntity>>
}