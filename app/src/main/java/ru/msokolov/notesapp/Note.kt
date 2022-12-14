package ru.msokolov.notesapp

import androidx.room.Entity

@Entity
data class Note(
    private val id: Long,
    private var title: String,
    private var isDone: Boolean
)
