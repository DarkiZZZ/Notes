package ru.msokolov.notesapp.model.notes.entities

data class Note(
    val id: Long,
    var title: String,
    var isDone: Boolean
)
