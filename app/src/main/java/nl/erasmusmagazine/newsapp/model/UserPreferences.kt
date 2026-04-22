package nl.erasmusmagazine.newsapp.model

data class UserPreferences(
    val language: AppLanguage = AppLanguage.DUTCH,
    val pushEnabled: Boolean = true,
    val userName: String? = null
)
