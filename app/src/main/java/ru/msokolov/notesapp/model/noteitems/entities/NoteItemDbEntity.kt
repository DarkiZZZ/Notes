package ru.msokolov.notesapp.model.noteitems.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note_items_table")
data class NoteItemDbEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    var text: String,
    @ColumnInfo(name = "is_done") var isDone: Boolean
)
