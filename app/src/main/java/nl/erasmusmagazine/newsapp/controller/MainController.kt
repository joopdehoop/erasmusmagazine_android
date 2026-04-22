package nl.erasmusmagazine.newsapp.controller

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import nl.erasmusmagazine.newsapp.data.repository.ArticlesRepository
import nl.erasmusmagazine.newsapp.data.repository.SettingsRepository
import nl.erasmusmagazine.newsapp.model.AppLanguage
import nl.erasmusmagazine.newsapp.model.Article
import nl.erasmusmagazine.newsapp.model.UserPreferences

class MainController(
    private val settingsRepository: SettingsRepository,
    private val articlesRepository: ArticlesRepository
) {
    fun loadPreferences(): UserPreferences = settingsRepository.getPreferences()

    fun updateLanguage(language: AppLanguage) {
        settingsRepository.saveLanguage(language)
    }

    fun updatePush(enabled: Boolean) {
        settingsRepository.savePushEnabled(enabled)
    }

    fun updateUser(name: String) {
        settingsRepository.saveUserName(name)
    }

    fun loadArticles(
        language: AppLanguage,
        onSuccess: (List<Article>) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            runCatching {
                articlesRepository.loadArticles(language)
            }.onSuccess { articles ->
                withContext(Dispatchers.Main) { onSuccess(articles) }
            }.onFailure { throwable ->
                withContext(Dispatchers.Main) { onError(throwable) }
            }
        }
    }
}
