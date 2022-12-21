package ru.msokolov.notesapp.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.msokolov.notesapp.data.AppDatabase
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
            AppDatabase::class.java,
            NOTE_DATABASE
        ) .fallbackToDestructiveMigration().build().getNoteDao()


    @Singleton
    @Provides
    fun provideItemDao(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            NOTE_DATABASE
        ) .fallbackToDestructiveMigration().build().getItemDao()
}