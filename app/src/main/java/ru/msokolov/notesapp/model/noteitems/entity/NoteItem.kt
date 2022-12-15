package ru.msokolov.notesapp.model.noteitems.entity

data class NoteItem(
    val id: Long,
    var text: String,
    var isDone: Boolean
)
