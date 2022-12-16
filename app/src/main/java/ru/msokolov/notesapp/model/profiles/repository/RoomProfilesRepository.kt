package ru.msokolov.notesapp.model.profiles.repository

import androidx.lifecycle.LiveData
import ru.msokolov.notesapp.model.profiles.ProfilesDao
import ru.msokolov.notesapp.model.profiles.entities.ProfileDbEntity

class RoomProfilesRepository(
    private val profilesDao: ProfilesDao
): ProfilesRepository {

    override suspend fun createNewProfile(profileDbEntity: ProfileDbEntity) {
        profilesDao.create(profileDbEntity)
    }

    override suspend fun updateProfile(profileDbEntity: ProfileDbEntity) {
        profilesDao.update(profileDbEntity)
    }

    override suspend fun deleteProfile(profileDbEntity: ProfileDbEntity) {
        profilesDao.delete(profileDbEntity)
    }

    override fun findById(profileId: Long): LiveData<ProfileDbEntity> {
        return profilesDao.findById(profileId)
    }

    override fun getAllProfiles(): LiveData<List<ProfileDbEntity>> {
        return profilesDao.getAllProfiles()
    }
}