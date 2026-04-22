package nl.erasmusmagazine.newsapp.data.repository

import nl.erasmusmagazine.newsapp.data.network.WordPressApi
import nl.erasmusmagazine.newsapp.model.Article

class ArticlesRepository(
    private val api: WordPressApi
) {
    suspend fun loadArticles(): List<Article> {
        return api.getPosts().map { dto ->
            Article(
                id = dto.id,
                title = dto.title.rendered,
                publishedAt = dto.date,
                author = dto.embedded?.author?.firstOrNull()?.name ?: "Erasmus Magazine",
                url = dto.link
            )
        }
    }
}
