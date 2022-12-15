package ru.msokolov.notesapp.model.profiles.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import ru.msokolov.notesapp.model.profiles.entity.Profile
import ru.msokolov.notesapp.model.profiles.room.entity.ProfileDbEntity

@Dao
interface ProfilesDao {

    @Query("SELECT * FROM profiles WHERE id = :id")
    fun findProfileById(id: Long): Flow<ProfileDbEntity?>

    @Update
    suspend fun updateProfile(profileDbEntity: ProfileDbEntity)

    @Insert
    suspend fun createProfile(profileDbEntity: ProfileDbEntity)

    @Query("SELECT * FROM profiles")
    fun getAllProfiles(): Flow<ProfileDbEntity?>
}