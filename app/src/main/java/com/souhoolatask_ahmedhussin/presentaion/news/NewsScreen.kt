package com.souhoolatask_ahmedhussin.presentaion.news

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.souhoolatask_ahmedhussin.domain.dto.Article
import com.souhoolatask_ahmedhussin.presentaion.composables.NewsItem
import com.souhoolatask_ahmedhussin.presentaion.composables.SearchBar

@Composable
fun NewsScreenSetup(
    viewModel: NewsViewModel = hiltViewModel()
) {
    val query by viewModel.query.collectAsState()
    val sortBy by viewModel.sortBy.collectAsState()
    val news = viewModel.news.collectAsLazyPagingItems()

    LaunchedEffect(Unit) {
        Log.e("NewsScreenSetup", "collectAsLazyPagingItems is collecting news")
    }
    NewsScreenContent(
        articles = news,
        query = query,
        sortBy = sortBy,
        onQueryChanged = viewModel::onQueryChanged,
        onSortByChanged = viewModel::onSortByChanged,
        onArticleClick = { /* Handle article click */ },
        onShareClick = { /* Handle share click */ }
    )

}

@Composable
fun NewsScreenContent(
    articles: LazyPagingItems<Article>,
    query: String,
    sortBy: String,
    onQueryChanged: (String) -> Unit,
    onSortByChanged: (String) -> Unit,
    onArticleClick: (Article) -> Unit,
    onShareClick: (Article) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        SearchBar(
            query = query,
            onQueryChanged = onQueryChanged,
            modifier = Modifier.fillMaxWidth(),
        )
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(articles.itemCount) { index ->
                val article = articles[index]
                if (article != null) {
                    NewsItem(
                        item = article,
                        onClick = { onArticleClick(article) },
                        onShareClick = { onShareClick(article) }
                    )
                }
            }
        }
    }

}

@Composable
fun <T : Any> fakeLazyPagingItems(data: List<T>): LazyPagingItems<T> {
    val pager = remember {
        Pager(PagingConfig(pageSize = data.size)) {
            object : PagingSource<Int, T>() {
                override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
                    return LoadResult.Page(
                        data = data,
                        prevKey = null,
                        nextKey = null
                    )
                }

                override fun getRefreshKey(state: PagingState<Int, T>): Int? = null
            }
        }
    }
    return pager.flow.collectAsLazyPagingItems()
}

fun fakeArticles() = listOf(
    Article(
        title = "Breaking News: Kotlin Compose FTW!",
        sourceName = "ComposeTimes",
        publishedDate = "2025-05-20",
        imageUrl = null,
        url = "https://example.com",
        content = "Compose has revolutionized UI development on Android."
    ),
    Article(
        title = "Jetpack Compose vs XML: The Showdown",
        sourceName = "DevWorld",
        publishedDate = "2025-05-18",
        imageUrl = null,
        url = "https://example.com",
        content = "Which one wins? The answer may surprise you."
    )
)

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun NewsScreenContentPreview() {
    val mockArticles = fakeArticles()
    val pagingItems = fakeLazyPagingItems(mockArticles)

    NewsScreenContent(
        articles = pagingItems,
        query = "",
        sortBy = "",
        onQueryChanged = {},
        onSortByChanged = {},
        onArticleClick = {},
        onShareClick = {}
    )

}