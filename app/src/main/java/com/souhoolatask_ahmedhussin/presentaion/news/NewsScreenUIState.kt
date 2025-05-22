package com.souhoolatask_ahmedhussin.presentaion.news

data class NewsUiState(
    val query: String = "",
    val sortBy: String = "publishedAt"
)