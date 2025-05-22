package com.souhoolatask_ahmedhussin.data.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.elegance_oud.util.state.ApiState
import com.elegance_oud.util.state.Resource
import com.souhoolatask_ahmedhussin.data.remote.WebServices
import com.souhoolatask_ahmedhussin.data.remote.mapper.toDomain
import com.souhoolatask_ahmedhussin.domain.dto.Article
import com.souhoolatask_ahmedhussin.domain.repository.NewsRepository
import com.souhoolatask_ahmedhussin.util.toResultFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class NewsRepositoryImpl(
    private val webServices: WebServices,
) : NewsRepository {
    override  fun getNews(query: String, sortBy: String): Flow<PagingData<Article>> {
        Log.e("getNews", "Fetching news with query=$query, sortBy=$sortBy")
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = {
                NewsPagingSource(webServices, query, sortBy)
            }
        ).flow
    }
}