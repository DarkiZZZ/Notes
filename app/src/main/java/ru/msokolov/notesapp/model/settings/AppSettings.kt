package ru.msokolov.notesapp.model.settings

interface AppSettings {

    fun getCurrentProfileId(): Long

    fun setCurrentProfileId(profileId: Long) //not necessary

    companion object {
        /**
         * Indicates that there is no logged-in profile.
         */
        const val NO_PROFILE_ID = -1L
    }
}