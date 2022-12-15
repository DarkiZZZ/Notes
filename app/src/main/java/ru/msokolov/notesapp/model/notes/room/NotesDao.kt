package ru.msokolov.notesapp.model.notes.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import ru.msokolov.notesapp.model.notes.entity.Note
import ru.msokolov.notesapp.model.notes.room.entity.NoteDbEntity

@Dao
interface NotesDao {

    @Query("SELECT * FROM notes WHERE id = :id")
    suspend fun findNoteById(id: Long): Note?

    @Update
    suspend fun updateNote(profileDbEntity: NoteDbEntity)

    @Insert
    suspend fun createNote(profileDbEntity: NoteDbEntity)

    @Query("SELECT * FROM notes")
    fun getAllNotes(): Flow<NoteDbEntity?>
}