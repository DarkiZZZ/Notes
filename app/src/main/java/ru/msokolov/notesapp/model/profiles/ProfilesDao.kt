package ru.msokolov.notesapp.model.profiles

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import ru.msokolov.notesapp.model.profiles.entities.ProfileDbEntity

@Dao
interface ProfilesDao {

    @Query("SELECT * FROM profiles_table WHERE id = :id")
    fun findById(id: Long): LiveData<ProfileDbEntity>

    @Update
    suspend fun update(profileDbEntity: ProfileDbEntity)

    @Insert
    suspend fun create(profileDbEntity: ProfileDbEntity)

    @Delete
    suspend fun delete(profileDbEntity: ProfileDbEntity)

    @Query("SELECT * FROM profiles_table")
    fun getAllProfiles(): LiveData<List<ProfileDbEntity>>
}