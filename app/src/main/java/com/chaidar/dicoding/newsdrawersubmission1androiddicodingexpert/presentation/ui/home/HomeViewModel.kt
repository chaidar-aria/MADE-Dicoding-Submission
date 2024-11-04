package com.chaidar.dicoding.newsdrawersubmission1androiddicodingexpert.presentation.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val newsUseCases: com.chaidar.dicoding.core.domain.usecase.NewsUseCases
) : ViewModel() {

    private val _newsList = MutableLiveData<List<com.chaidar.dicoding.core.domain.model.Article>>()
    val newsList: LiveData<List<com.chaidar.dicoding.core.domain.model.Article>> = _newsList

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    init {
        fetchNews("jokowi")
    }

    private fun fetchNews(query: String) {
        viewModelScope.launch {
            newsUseCases.getNewsList(query, "084624a3e5f04b2b851c241c70b5571f")
                .catch { e ->
                    _error.value = "Error: ${e.message}"
                }
                .collect { articles ->
                    _newsList.value = articles
                }
        }
    }
}