package ru.msokolov.notesapp.data.room.item

import javax.inject.Inject

class ItemRepository @Inject constructor(private val itemDao: ItemDao) {

    suspend fun insert(itemEntity: ItemEntity) = itemDao.insert(itemEntity)

    suspend fun updateData(itemEntity: ItemEntity) = itemDao.update(itemEntity)

    suspend fun deleteItem(itemEntity: ItemEntity) = itemDao.delete(itemEntity)

    suspend fun deleteAllByNoteId(noteId: Int) {
        itemDao.deleteAllByNoteId(noteId)
    }

    suspend fun deleteAll(){
        itemDao.deleteAll()
    }

    fun getAllItems(noteId: Int) = itemDao.getAllItems(noteId)

}