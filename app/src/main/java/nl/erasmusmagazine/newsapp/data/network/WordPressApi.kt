package nl.erasmusmagazine.newsapp.data.network

import retrofit2.http.GET
import retrofit2.http.Query

interface WordPressApi {
    @GET("/wp-json/wp/v2/posts")
    suspend fun getPosts(
        @Query("per_page") pageSize: Int = 20,
        @Query("_embed") includeEmbedded: Boolean = true
    ): List<WordPressPostDto>
}
