package ru.msokolov.notesapp.model.noteitems

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ru.msokolov.notesapp.model.noteitems.entities.NoteItem
import ru.msokolov.notesapp.model.noteitems.entities.NoteItemDbEntity
import ru.msokolov.notesapp.model.profiles.entities.ProfileDbEntity

@Dao
interface NoteItemsDao {

    @Query("SELECT * FROM note_items_table WHERE id = :id")
    fun findById(id: Long): LiveData<NoteItemDbEntity>

    @Update
    suspend fun update(noteItemDbEntity: NoteItemDbEntity)

    @Insert
    suspend fun create(noteItemDbEntity: NoteItemDbEntity)

    @Delete
    suspend fun delete(noteItemDbEntity: NoteItemDbEntity)

    @Query("SELECT * FROM note_items_table")
    fun getAllProfiles(): LiveData<List<NoteItemDbEntity>>
}