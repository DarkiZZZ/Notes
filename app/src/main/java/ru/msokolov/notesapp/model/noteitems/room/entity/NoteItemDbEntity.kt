package ru.msokolov.notesapp.model.noteitems.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.msokolov.notesapp.model.noteitems.entity.NoteItem

@Entity(tableName = "note_items")
data class NoteItemDbEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    var text: String,
    @ColumnInfo(name = "is_done") var isDone: Boolean
){
    //Этот метод преобразует класс данных БД в обычный класс данных для работы во ViewModel и тд.
    fun toNoteItem(): NoteItem = NoteItem(
        id = id,
        text = text,
        isDone = isDone
    )
}
