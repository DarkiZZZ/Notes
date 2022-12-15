package ru.msokolov.notesapp.model.profiles.room

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import ru.msokolov.notesapp.model.profiles.ProfilesRepository
import ru.msokolov.notesapp.model.profiles.entity.Profile
import ru.msokolov.notesapp.model.profiles.room.entity.ProfileDbEntity
import ru.msokolov.notesapp.model.settings.AppSettings
import ru.msokolov.notesapp.utils.AsyncLoader

class RoomProfilesRepository(
    private val profilesDao: ProfilesDao,
    private val appSettings: AppSettings,
    private val ioDispatcher: CoroutineDispatcher
): ProfilesRepository {

    private val currentProfileIdFlow = AsyncLoader{
        MutableStateFlow(ProfileId(appSettings.getCurrentProfileId()))
    }

    override suspend fun getProfile(): Flow<Profile?> {
        return currentProfileIdFlow.get()
            .flatMapLatest { profileId ->
                if (profileId.value == AppSettings.NO_PROFILE_ID) {
                    flowOf(null)
                } else {
                    getProfileById(profileId.value)
                }
            }
            .flowOn(ioDispatcher)
    }

    override suspend fun updateProfile(newName: String, newPhotoId: Int) {
        val profileId = appSettings.getCurrentProfileId()

        updateProfile(profileId, newName, newPhotoId)

        currentProfileIdFlow.get().value = ProfileId(profileId)
    }

    private suspend fun createAccount(profile: Profile) {
        val entity = ProfileDbEntity.toProfileDbEntity(profile)
        profilesDao.createProfile(entity)
    }

    private fun getAllProfiles(): Flow<Profile?> {
        return profilesDao.getAllProfiles().map { it?.toProfile() }
    }

    private fun getProfileById(profileId: Long): Flow<Profile?> {
        return profilesDao.findProfileById(profileId).map { it?.toProfile() }
    }

    private suspend fun updateProfile(accountId: Long, newName: String, newPhotoId: Int) {
        profilesDao.updateProfile(ProfileDbEntity(accountId, newName, newPhotoId))
    }

    private class ProfileId(val value: Long)

}