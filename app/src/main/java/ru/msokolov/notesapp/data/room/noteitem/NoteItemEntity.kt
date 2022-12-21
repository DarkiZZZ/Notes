package ru.msokolov.notesapp.data.room.noteitem

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import kotlinx.parcelize.Parcelize
import ru.msokolov.notesapp.util.Constants

//one-to-many
/*@Parcelize
@Entity(tableName = Constants.NOTE_ITEM_TABLE)
data class NoteItemEntity(
    @ColumnInfo(name = "note_id")
    var noteId: Int,
    @ColumnInfo(name = "item_id")
    var itemId: Int
): Parcelable*/
