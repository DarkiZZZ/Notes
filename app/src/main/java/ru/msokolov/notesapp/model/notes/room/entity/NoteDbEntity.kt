package ru.msokolov.notesapp.model.notes.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.msokolov.notesapp.model.notes.entity.Note

@Entity(tableName = "notes")
data class NoteDbEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    var title: String,
    @ColumnInfo(name = "is_done") var isDone: Boolean
){
    fun toNote(): Note = Note(
        id =  id,
        title = title,
        isDone = isDone
    )
}
