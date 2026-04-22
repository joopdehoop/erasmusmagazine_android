package nl.erasmusmagazine.newsapp.data.repository

import androidx.core.text.HtmlCompat
import nl.erasmusmagazine.newsapp.data.network.WordPressApi
import nl.erasmusmagazine.newsapp.model.AppLanguage
import nl.erasmusmagazine.newsapp.model.Article

class ArticlesRepository(
    private val api: WordPressApi
) {
    suspend fun loadArticles(language: AppLanguage): List<Article> {
        return api.getPosts(url = language.postsApiUrl).map { dto ->
            Article(
                id = dto.id,
                title = HtmlCompat.fromHtml(dto.title.rendered, HtmlCompat.FROM_HTML_MODE_LEGACY).toString(),
                publishedAt = dto.date,
                author = dto.embedded?.author?.firstOrNull()?.name ?: "Erasmus Magazine",
                url = dto.link
            )
        }
    }
}
