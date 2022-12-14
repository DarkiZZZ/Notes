package ru.msokolov.notesapp.model.noteitems.entity

import androidx.room.Entity

@Entity
data class NoteItem(
    private val id: Long,
    private var text: String,
    private var isDone: Boolean
)
