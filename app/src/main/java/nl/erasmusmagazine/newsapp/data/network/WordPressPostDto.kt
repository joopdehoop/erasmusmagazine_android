package nl.erasmusmagazine.newsapp.data.network

import com.squareup.moshi.Json

data class WordPressPostDto(
    val id: Long,
    val date: String,
    val link: String,
    val title: RenderedField,
    @Json(name = "_embedded") val embedded: Embedded? = null
)

data class RenderedField(
    val rendered: String
)

data class Embedded(
    val author: List<AuthorDto> = emptyList()
)

data class AuthorDto(
    val name: String
)
