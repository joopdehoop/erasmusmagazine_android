package nl.erasmusmagazine.newsapp.model

data class Article(
    val id: Long,
    val title: String,
    val publishedAt: String,
    val author: String,
    val url: String
)
