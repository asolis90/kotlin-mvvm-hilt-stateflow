package com.asolis.kotlinmvvmhilt.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DataResponse(
    val status: String = "",
    val totalResults: Int = 0,
    val articles: List<Article> = ArrayList(),
): Parcelable