package ru.msokolov.notesapp.data.room.item

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import ru.msokolov.notesapp.util.Constants

@Parcelize
@Entity(tableName = Constants.ITEM_TABLE)
data class ItemEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    @ColumnInfo(name = "parent_id") var parentId: Int,
    var text: String
) : Parcelable