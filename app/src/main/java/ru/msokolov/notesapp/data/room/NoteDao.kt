package ru.msokolov.notesapp.data.room

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Insert
    suspend fun insert(noteEntity: NoteEntity)

    @Delete
    suspend fun delete(noteEntity: NoteEntity)

    @Update
    suspend fun update(noteEntity: NoteEntity)

    @Query("DELETE FROM note_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM note_table ORDER BY timestamp DESC")
    fun getAllTasks(): Flow<List<NoteEntity>>

    @Query("SELECT * FROM note_table ORDER BY priority ASC")
    fun getAllPriorityTasks(): Flow<List<NoteEntity>>

    @Query("SELECT * FROM note_table WHERE title LIKE :searchQuery ORDER BY timestamp DESC")
    fun searchDatabase(searchQuery: String): LiveData<List<NoteEntity>>
}