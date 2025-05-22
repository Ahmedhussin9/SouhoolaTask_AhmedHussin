package com.souhoolatask_ahmedhussin.data.remote.mapper

import com.souhoolatask_ahmedhussin.data.reponse.ArticlesItem
import com.souhoolatask_ahmedhussin.domain.dto.Article

fun ArticlesItem.toDomain(): Article {
    return Article(
        title = title.orEmpty(),
        url = url.orEmpty(),
        imageUrl = urlToImage,
        publishedDate = publishedAt.orEmpty(),
        content = content,
        sourceName = source?.name.orEmpty()
    )
}