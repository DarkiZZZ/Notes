package ru.msokolov.notesapp.model.room

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.msokolov.notesapp.model.noteitems.room.NoteItemsDao
import ru.msokolov.notesapp.model.notes.room.NotesDao
import ru.msokolov.notesapp.model.profiles.room.ProfilesDao
import ru.msokolov.notesapp.model.profiles.room.entity.ProfileDbEntity

@Database(
    version = 1,
    entities = [
        ProfileDbEntity::class
    ]
)
abstract class AppDataBase: RoomDatabase() {

    abstract fun getProfilesDao(): ProfilesDao
    abstract fun getNotesDao(): NotesDao
    abstract fun getNoteItemsDao(): NoteItemsDao
}