package ru.msokolov.notesapp.data.room.item

import javax.inject.Inject

class ItemRepository @Inject constructor(private val itemDao: ItemDao) {

    suspend fun insertOrUpdate(itemEntity: ItemEntity) = itemDao.insertOrUpdate(itemEntity)

    suspend fun deleteItem(itemEntity: ItemEntity) = itemDao.delete(itemEntity)

    suspend fun deleteAllByNoteId(noteId: Int) {
        itemDao.deleteAllDataByNoteId(noteId)
    }

    suspend fun deleteAllData(){
        itemDao.deleteAllData()
    }

    fun getAllItemsByNoteId(noteId: Int) = itemDao.getAllItemsByNoteId(noteId)
}