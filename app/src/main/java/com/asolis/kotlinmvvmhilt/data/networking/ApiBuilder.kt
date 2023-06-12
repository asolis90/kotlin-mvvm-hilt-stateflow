package com.asolis.kotlinmvvmhilt.data.networking
import android.content.Context
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiBuilder(private val context: Context, private val apiConfiguration: ApiConfiguration) {
    fun <T> buildApi(baseURL: String, tClass: Class<T>): T {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseURL)
            .client(apiConfiguration.getClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(tClass)
    }

    companion object Factory {
    }
}