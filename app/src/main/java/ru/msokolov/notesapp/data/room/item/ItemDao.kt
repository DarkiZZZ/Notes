package ru.msokolov.notesapp.data.room.item

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(itemEntity: ItemEntity)

    @Delete
    suspend fun delete(itemEntity: ItemEntity)

    @Update
    suspend fun update(itemEntity: ItemEntity)

    @Query("DELETE FROM item_table WHERE parent_id =:noteId")
    suspend fun deleteAll(noteId: Int)

    @Query("SELECT * FROM item_table WHERE parent_id =:noteId")
    fun getAllItems(noteId: Int): Flow<List<ItemEntity>>

}