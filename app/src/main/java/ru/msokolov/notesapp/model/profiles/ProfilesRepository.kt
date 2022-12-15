package ru.msokolov.notesapp.model.profiles

import kotlinx.coroutines.flow.Flow
import ru.msokolov.notesapp.model.profiles.entity.Profile

interface ProfilesRepository {

    suspend fun getProfile(): Flow<Profile?>

    suspend fun updateProfile(newName: String, newPhotoId: Int)
}