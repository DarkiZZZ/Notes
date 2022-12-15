package ru.msokolov.notesapp.model.noteitems.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import ru.msokolov.notesapp.model.noteitems.entity.NoteItem
import ru.msokolov.notesapp.model.noteitems.room.entity.NoteItemDbEntity

@Dao
interface NoteItemsDao {

    @Query("SELECT * FROM note_items WHERE id = :id")
    suspend fun findNoteItemById(id: Long): NoteItem?

    @Update
    suspend fun updateNoteItem(profileDbEntity: NoteItemDbEntity)

    @Insert
    suspend fun createNoteItem(profileDbEntity: NoteItemDbEntity)

    @Query("SELECT * FROM profiles")
    fun getAllNoteItems(): Flow<NoteItemDbEntity?>
}