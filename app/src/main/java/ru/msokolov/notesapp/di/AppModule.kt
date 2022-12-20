package ru.msokolov.notesapp.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.msokolov.notesapp.data.room.NoteDatabase
import ru.msokolov.notesapp.util.Constants.NOTE_DATABASE
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideNoteDao(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context,
            NoteDatabase::class.java,
            NOTE_DATABASE
        ).build().getNoteDao()
}