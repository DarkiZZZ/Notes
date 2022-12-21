package ru.msokolov.notesapp.data.room.noteitem

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


/*
interface NoteItemDao {

    @Delete
    suspend fun deleteOneItem(noteItemEntity: NoteItemEntity)

    @Query("DELETE FROM note_item_table WHERE note_id =:noteId")
    suspend fun deleteAllItemsFromNote(noteId: Int)

}*/
