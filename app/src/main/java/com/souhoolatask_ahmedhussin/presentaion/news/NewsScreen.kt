package com.souhoolatask_ahmedhussin.presentaion.news

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.souhoolatask_ahmedhussin.domain.dto.Article
import com.souhoolatask_ahmedhussin.presentaion.composables.NewsItem
import com.souhoolatask_ahmedhussin.presentaion.composables.SearchBar
import kotlinx.coroutines.launch

@Composable
fun NewsScreenSetup(
    viewModel: NewsViewModel = hiltViewModel()
) {
    val inputQuery by viewModel.inputQuery.collectAsState()
    val sortBy by viewModel.sortBy.collectAsState()
    val news = viewModel.news.collectAsLazyPagingItems()

    NewsScreenContent(
        articles = news,
        inputQuery = inputQuery,
        sortBy = sortBy,
        onQueryChanged = viewModel::onInputQueryChanged,
        onSearchDone = viewModel::onSearchDone,
        onSortByChanged = viewModel::onSortByChanged,
        onArticleClick = { /* Handle article click */ },
        onShareClick = { /* Handle share click */ },
    )

}

@Composable
fun NewsScreenContent(
    articles: LazyPagingItems<Article>,
    inputQuery: String,
    onQueryChanged: (String) -> Unit,
    onSearchDone: () -> Unit,
    onArticleClick: (Article) -> Unit,
    onShareClick: (Article) -> Unit,
    onSortByChanged: (String) -> Unit,
    sortBy: String,
) {
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()


    val isRefreshing = articles.loadState.refresh is LoadState.Loading

    val showScrollToTop by remember {
        derivedStateOf { listState.firstVisibleItemIndex > 5 }
    }
    LaunchedEffect(isRefreshing) {
        listState.animateScrollToItem(0)
    }
    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing = isRefreshing),
        onRefresh = { articles.refresh() }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.fillMaxSize()) {
                SearchBar(
                    query = inputQuery,
                    onQueryChanged = onQueryChanged,
                    onSearchDone = onSearchDone,
                    modifier = Modifier.fillMaxWidth()
                )

                when (val state = articles.loadState.refresh) {
                    is LoadState.Loading -> {
                    }

                    is LoadState.Error -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = "Error: ${state.error.message}",
                                    color = Color.Red,
                                    modifier = Modifier.padding(16.dp)
                                )
                                Button(onClick = { articles.retry() }) {
                                    Text("Retry")
                                }
                            }
                        }
                    }

                    is LoadState.NotLoading -> {
                        if (articles.itemCount == 0) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(24.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Icon(
                                        imageVector = Icons.Default.Info,
                                        contentDescription = "No News",
                                        tint = Color.Gray,
                                        modifier = Modifier.size(64.dp)
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        text = "No news found. Try a different search term.",
                                        style = MaterialTheme.typography.bodyMedium,
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                        } else {
                            LazyColumn(
                                state = listState,
                                modifier = Modifier.fillMaxSize()
                            ) {
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

                                when (articles.loadState.append) {
                                    is LoadState.Loading -> item {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(16.dp),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            CircularProgressIndicator()
                                        }
                                    }

                                    is LoadState.Error -> item {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.Center
                                        ) {
                                            Text(
                                                text = "Can't find any more news",
                                                color = Color.Black,
                                                fontSize = 18.sp,
                                                fontWeight = FontWeight.Medium
                                            )
                                        }
                                    }

                                    else -> Unit
                                }
                            }
                        }
                    }

                }
            }

            if (showScrollToTop) {
                FloatingActionButton(
                    onClick = {
                        coroutineScope.launch {
                            listState.animateScrollToItem(0)
                        }
                    },
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(16.dp)
                ) {
                    Icon(Icons.Default.KeyboardArrowUp, contentDescription = "Scroll to Top")
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
        inputQuery = "",
        sortBy = "",
        onQueryChanged = {},
        onSortByChanged = {},
        onArticleClick = {},
        onShareClick = {},
        onSearchDone = {},

    )

}