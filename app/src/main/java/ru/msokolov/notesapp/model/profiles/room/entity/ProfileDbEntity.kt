package ru.msokolov.notesapp.model.profiles.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.msokolov.notesapp.model.profiles.entity.Profile

@Entity(tableName = "profiles")
data class ProfileDbEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    var name: String,
    @ColumnInfo(name = "photo_id") var photoId: Int
){
    fun toProfile(): Profile = Profile(
        id = id,
        name = name,
        photoId = photoId
    )

    companion object{
        fun toProfileDbEntity(profile: Profile) = ProfileDbEntity(
            id = profile.id,
            name = profile.name,
            photoId = profile.photoId
        )
    }
}
