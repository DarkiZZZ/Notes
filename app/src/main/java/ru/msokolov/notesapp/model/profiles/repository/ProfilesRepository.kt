package ru.msokolov.notesapp.model.profiles.repository

import androidx.lifecycle.LiveData
import ru.msokolov.notesapp.model.profiles.entities.ProfileDbEntity

interface ProfilesRepository {

    suspend fun createNewProfile(profileDbEntity: ProfileDbEntity)

    suspend fun updateProfile(profileDbEntity: ProfileDbEntity)

    suspend fun deleteProfile(profileDbEntity: ProfileDbEntity)

    fun findById(profileId: Long): LiveData<ProfileDbEntity>

    fun getAllProfiles(): LiveData<List<ProfileDbEntity>>
}