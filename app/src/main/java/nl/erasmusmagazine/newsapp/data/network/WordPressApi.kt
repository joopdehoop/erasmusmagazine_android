package nl.erasmusmagazine.newsapp.data.network

import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface WordPressApi {
    @GET
    suspend fun getPosts(
        @Url url: String,
        @Query("per_page") pageSize: Int = 20,
        @Query("_embed") includeEmbedded: Boolean = true
    ): List<WordPressPostDto>
}
