package com.souhoolatask_ahmedhussin.presentaion.news

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.souhoolatask_ahmedhussin.domain.dto.Article
import com.souhoolatask_ahmedhussin.domain.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsRepository: NewsRepository
) : ViewModel() {
    private val _inputQuery = MutableStateFlow("")
    val inputQuery: StateFlow<String> = _inputQuery

    private val _query = MutableStateFlow("")
    private val _sortBy = MutableStateFlow("publishedAt")
    val query: StateFlow<String> = _query
    val sortBy: StateFlow<String> = _sortBy

    val news: StateFlow<PagingData<Article>> = combine(_query, _sortBy) { query, sortBy ->
        if (query.isBlank()) flowOf(PagingData.empty())
        else newsRepository.getNews(query, sortBy)
    }.flatMapLatest { it }
        .cachedIn(viewModelScope)
        .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())


    fun onInputQueryChanged(text: String) {
        _inputQuery.value = text
    }
    fun onSearchDone() {
        _query.value = _inputQuery.value.trim()
    }
    fun onSortByChanged(sort: String) {
        _sortBy.value = sort
    }
}