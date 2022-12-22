package ru.msokolov.notesapp.data.room.item

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(itemEntity: ItemEntity)

    @Delete
    suspend fun delete(itemEntity: ItemEntity)

    @Query("DELETE FROM item_table WHERE parent_id =:noteId")
    suspend fun deleteAllDataByNoteId(noteId: Int)

    @Query("DELETE FROM item_table")
    suspend fun deleteAllData()

    @Query("SELECT * FROM item_table WHERE parent_id =:noteId")
    fun getAllItemsByNoteId(noteId: Int): Flow<List<ItemEntity>>
}