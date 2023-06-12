package com.asolis.kotlinmvvmhilt.data.networking

import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

class ApiConfiguration(private val cache: Cache?, private var interceptor: Interceptor) {
    fun getClient(): OkHttpClient {

        val builder: OkHttpClient.Builder = OkHttpClient.Builder()
        builder.connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
        if (cache != null) {
            builder.cache(cache)
        }
        return builder.build()
    }
}