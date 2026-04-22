package nl.erasmusmagazine.newsapp.util

import nl.erasmusmagazine.newsapp.data.network.WordPressApi
import nl.erasmusmagazine.newsapp.model.AppLanguage
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object NetworkFactory {
    fun createWordPressApi(language: AppLanguage): WordPressApi {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        return Retrofit.Builder()
            .baseUrl(language.baseUrl)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(client)
            .build()
            .create(WordPressApi::class.java)
    }
}
