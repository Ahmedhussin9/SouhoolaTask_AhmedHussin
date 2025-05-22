package com.souhoolatask_ahmedhussin.domain.dto

data class Article(
    val title: String,
    val url: String,
    val imageUrl: String?,
    val publishedDate: String,
    val content: String?,
    val sourceName: String
)
