package nl.erasmusmagazine.newsapp.data.repository

import android.content.Context
import android.content.SharedPreferences
import nl.erasmusmagazine.newsapp.model.AppLanguage
import nl.erasmusmagazine.newsapp.model.UserPreferences

class SettingsRepository(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE)

    fun getPreferences(): UserPreferences {
        return UserPreferences(
            language = AppLanguage.fromCode(prefs.getString(KEY_LANGUAGE, null)),
            pushEnabled = prefs.getBoolean(KEY_PUSH_ENABLED, true),
            userName = prefs.getString(KEY_USER_NAME, null)
        )
    }

    fun saveLanguage(language: AppLanguage) {
        prefs.edit().putString(KEY_LANGUAGE, language.code).apply()
    }

    fun savePushEnabled(enabled: Boolean) {
        prefs.edit().putBoolean(KEY_PUSH_ENABLED, enabled).apply()
    }

    fun saveUserName(name: String) {
        prefs.edit().putString(KEY_USER_NAME, name).apply()
    }

    companion object {
        private const val PREF_FILE = "em_settings"
        private const val KEY_LANGUAGE = "language"
        private const val KEY_PUSH_ENABLED = "push_enabled"
        private const val KEY_USER_NAME = "user_name"
    }
}
