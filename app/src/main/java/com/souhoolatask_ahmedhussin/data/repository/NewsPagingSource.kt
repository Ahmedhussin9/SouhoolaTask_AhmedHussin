package com.souhoolatask_ahmedhussin.data.repository

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.souhoolatask_ahmedhussin.data.remote.WebServices
import com.souhoolatask_ahmedhussin.data.remote.mapper.toDomain
import com.souhoolatask_ahmedhussin.domain.dto.Article
import com.souhoolatask_ahmedhussin.util.apiKey

class NewsPagingSource(
    private val api: WebServices,
    private val query: String,
    private val sortBy: String
) : PagingSource<Int, Article>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        Log.d("NewsPagingSource", "Loading page: ${params.key}")
        val page = params.key ?: 1
        return try {
            val response = api.getNews(
                query = query,
                sortBy = sortBy,
                page = page,
                pageSize = params.loadSize,
                apiKey = apiKey
            )

            val articles = response.articles?.map { it?.toDomain() ?: Article(
                title = "No title",
                sourceName = "No source",
                publishedDate = "No date",
                imageUrl = null,
                url = "No url",
                content = "No content"
            ) } ?: emptyList()
            LoadResult.Page(
                data = articles,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (articles.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            Log.e("error", e.toString(), )
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Article>): Int = 1
}
