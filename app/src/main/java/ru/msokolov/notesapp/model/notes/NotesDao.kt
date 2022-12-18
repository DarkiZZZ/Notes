package ru.msokolov.notesapp.model.notes

import androidx.lifecycle.LiveData
import androidx.room.*
import ru.msokolov.notesapp.model.notes.entities.NoteDbEntity

@Dao
interface NotesDao {

    @Query("SELECT * FROM notes_table WHERE id = :id")
    fun findById(id: Long): LiveData<NoteDbEntity>

    @Update
    suspend fun update(noteDbEntity: NoteDbEntity)

    @Insert
    suspend fun create(noteDbEntity: NoteDbEntity)

    @Delete
    suspend fun delete(noteDbEntity: NoteDbEntity)

    @Query("SELECT * FROM notes_table")
    fun getAllNotes(): LiveData<List<NoteDbEntity>>
}