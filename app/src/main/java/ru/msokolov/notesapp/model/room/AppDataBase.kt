package ru.msokolov.notesapp.model.room

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.msokolov.notesapp.model.noteitems.NoteItemsDao
import ru.msokolov.notesapp.model.notes.NotesDao
import ru.msokolov.notesapp.model.profiles.ProfilesDao
import ru.msokolov.notesapp.model.profiles.entities.ProfileDbEntity

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