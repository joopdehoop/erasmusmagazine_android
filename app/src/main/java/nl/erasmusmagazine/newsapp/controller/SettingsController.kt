package nl.erasmusmagazine.newsapp.controller

import nl.erasmusmagazine.newsapp.data.repository.SettingsRepository
import nl.erasmusmagazine.newsapp.model.UserPreferences

class SettingsController(
    private val settingsRepository: SettingsRepository
) {
    fun loadPreferences(): UserPreferences = settingsRepository.getPreferences()

    fun saveUserName(name: String) {
        settingsRepository.saveUserName(name)
    }

    fun savePushEnabled(enabled: Boolean) {
        settingsRepository.savePushEnabled(enabled)
    }
}
