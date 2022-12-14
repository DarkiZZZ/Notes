package ru.msokolov.notesapp

import androidx.room.Entity

@Entity
data class Profile(
    private val id: Long,
    private var name: String,
    private var photoId: Int
)