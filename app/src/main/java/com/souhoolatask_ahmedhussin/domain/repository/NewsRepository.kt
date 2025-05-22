package com.souhoolatask_ahmedhussin.domain.repository

import androidx.paging.PagingData
import com.elegance_oud.util.state.Resource
import com.souhoolatask_ahmedhussin.domain.dto.Article
import kotlinx.coroutines.flow.Flow


interface NewsRepository {
     fun getNews(
        query: String,
        sortBy: String
    ):Flow<PagingData<Article>>
}