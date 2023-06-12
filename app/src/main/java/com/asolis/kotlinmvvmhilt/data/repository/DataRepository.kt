package com.asolis.kotlinmvvmhilt.data.repository

import com.asolis.kotlinmvvmhilt.data.models.Article
import kotlinx.coroutines.flow.Flow

interface DataRepository {
    fun getTopNews(country: String): Flow<List<Article>>
}