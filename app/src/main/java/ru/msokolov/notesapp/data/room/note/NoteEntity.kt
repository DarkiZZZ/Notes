package ru.msokolov.notesapp.data.room.note

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import ru.msokolov.notesapp.util.Constants

@Parcelize
@Entity(tableName = Constants.NOTE_TABLE)
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var title: String,
    var priority: Int,
    var timestamp: Long
) : Parcelable
