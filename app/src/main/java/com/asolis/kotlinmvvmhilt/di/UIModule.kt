package com.asolis.kotlinmvvmhilt.di

import android.content.Context
import com.asolis.kotlinmvvmhilt.data.networking.ApiBuilder
import com.asolis.kotlinmvvmhilt.data.networking.ApiConfiguration
import com.asolis.kotlinmvvmhilt.data.networking.NewsAPI
import com.asolis.kotlinmvvmhilt.data.repository.DataRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.Request
import java.io.File
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UIModule {

    @Singleton
    @Provides
    fun provideDataRepository(@ApplicationContext context: Context): DataRepositoryImpl {
        // cache size of 10MB
        val cacheSize = 10 * 1024 * 1024
        val cache = Cache(File(context.cacheDir, NewsAPI.HTTP_CACHE_FILENAME), cacheSize.toLong())
        val apiBaseUrl: String = NewsAPI.BASE_URL

        return DataRepositoryImpl(
            ApiBuilder(context, ApiConfiguration(cache, Interceptor {
                val newRequest = Request.Builder()
                    .addHeader("X-Api-Key", NewsAPI.API_KEY)
                    .url(it.request().url())
                    .build()

                it.proceed(newRequest)

            })).buildApi(apiBaseUrl, NewsAPI::class.java)
        )
    }

}