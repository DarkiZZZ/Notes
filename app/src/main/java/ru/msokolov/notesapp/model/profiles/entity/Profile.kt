package ru.msokolov.notesapp.model.profiles.entity

import androidx.room.Entity

@Entity
data class Profile(
    private val id: Long,
    private var name: String,
    private var photoId: Int
)