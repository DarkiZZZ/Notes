package ru.msokolov.notesapp.model.settings

import android.content.Context
import ru.msokolov.notesapp.model.settings.AppSettings.Companion.NO_PROFILE_ID

class SharedPreferencesAppSettings(
    applicationContext: Context
) : AppSettings {

    private val sharedPreferences = applicationContext.getSharedPreferences("settings", Context.MODE_PRIVATE)

    override fun setCurrentProfileId(profileId: Long) {
        sharedPreferences.edit()
            .putLong(PREF_CURRENT_PROFILE_ID, profileId)
            .apply()
    }

    override fun getCurrentProfileId(): Long = sharedPreferences.getLong(PREF_CURRENT_PROFILE_ID, NO_PROFILE_ID)


    companion object {
        private const val PREF_CURRENT_PROFILE_ID = "currentProfileId"
    }
}