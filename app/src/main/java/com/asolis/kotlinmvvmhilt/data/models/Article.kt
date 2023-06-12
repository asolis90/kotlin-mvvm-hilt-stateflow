package com.asolis.kotlinmvvmhilt.data.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Article(
    val title: String = "",
    val description: String = "",
    val url: String = "",
    @SerializedName("urlToImage")
    val imageUrl: String = "",
    val source: Source
) : Parcelable