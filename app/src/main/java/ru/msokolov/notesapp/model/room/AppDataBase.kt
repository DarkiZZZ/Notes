package ru.msokolov.notesapp.model.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.msokolov.notesapp.model.noteitems.NoteItemsDao
import ru.msokolov.notesapp.model.noteitems.entities.NoteItemDbEntity
import ru.msokolov.notesapp.model.notes.NotesDao
import ru.msokolov.notesapp.model.notes.entities.NoteDbEntity
import ru.msokolov.notesapp.model.profiles.ProfilesDao
import ru.msokolov.notesapp.model.profiles.entities.ProfileDbEntity

@Database(
    version = 1,
    entities = [
        ProfileDbEntity::class,
        NoteDbEntity::class,
        NoteItemDbEntity::class
    ]
)
abstract class AppDataBase: RoomDatabase() {

    abstract fun getProfilesDao(): ProfilesDao
    abstract fun getNotesDao(): NotesDao
    abstract fun getNoteItemsDao(): NoteItemsDao

    companion object{
        @Volatile
        private var INSTANCE: AppDataBase? = null

                fun getDataBase(context: Context): AppDataBase{
                    synchronized(this){
                        var instance = INSTANCE
                        if (instance == null){
                            instance = Room.databaseBuilder(
                                context.applicationContext,
                                AppDataBase::class.java,
                                "app_database"
                            ).fallbackToDestructiveMigration()
                                .build()
                            INSTANCE = instance
                        }
                        return instance
                    }
                }
    }
}