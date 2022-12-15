package ru.msokolov.notesapp.model.notes.entity

data class Note(
    val id: Long,
    var title: String,
    var isDone: Boolean
)
