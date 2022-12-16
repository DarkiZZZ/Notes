package ru.msokolov.notesapp.model.profiles.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "profiles_table")
data class ProfileDbEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    var name: String,
    @ColumnInfo(name = "photo_id") var photoId: Int
)
