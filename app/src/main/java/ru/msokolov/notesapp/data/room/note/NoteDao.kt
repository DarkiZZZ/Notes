package ru.msokolov.notesapp.data.room.note

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(noteEntity: NoteEntity)

    @Delete
    suspend fun delete(noteEntity: NoteEntity)

    @Update
    suspend fun update(noteEntity: NoteEntity)

    @Query("DELETE FROM note_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM note_table ORDER BY timestamp DESC")
    fun getAllNotes(): Flow<List<NoteEntity>>

    @Query("SELECT * FROM note_table ORDER BY priority ASC")
    fun getAllPriorityNotes(): Flow<List<NoteEntity>>

    @Query("SELECT * FROM note_table WHERE title LIKE :searchQuery ORDER BY timestamp DESC")
    fun searchDatabase(searchQuery: String): LiveData<List<NoteEntity>>
}