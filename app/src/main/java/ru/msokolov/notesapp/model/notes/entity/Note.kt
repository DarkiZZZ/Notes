package ru.msokolov.notesapp.model.notes.entity

import androidx.room.Entity

@Entity
data class Note(
    private val id: Long,
    private var title: String,
    private var isDone: Boolean
)
