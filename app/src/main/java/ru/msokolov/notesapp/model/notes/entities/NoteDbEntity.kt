package ru.msokolov.notesapp.model.notes.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.msokolov.notesapp.model.notes.entities.Note

@Entity(tableName = "notes_table")
data class NoteDbEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    var title: String,
    @ColumnInfo(name = "is_done") var isDone: Boolean
)
