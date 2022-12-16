package ru.msokolov.notesapp.model.noteitems.entities

data class NoteItem(
    val id: Long,
    var text: String,
    var isDone: Boolean
)
