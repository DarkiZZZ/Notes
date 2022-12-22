package ru.msokolov.notesapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.msokolov.notesapp.data.room.item.ItemDao
import ru.msokolov.notesapp.data.room.item.ItemEntity
import ru.msokolov.notesapp.data.room.note.NoteDao
import ru.msokolov.notesapp.data.room.note.NoteEntity

@Database(entities = [NoteEntity::class, ItemEntity::class],
    version = 16,
exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun getNoteDao(): NoteDao
    abstract fun getItemDao(): ItemDao
}
