package com.chaidar.dicoding.newsdrawersubmission1androiddicodingexpert.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chaidar.dicoding.core.domain.usecase.NewsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val newsUseCases: NewsUseCases
) : ViewModel() {

    private val _favoriteArticles =
        MutableLiveData<List<com.chaidar.dicoding.core.domain.model.Article>>()
    val favoriteArticles: LiveData<List<com.chaidar.dicoding.core.domain.model.Article>> get() = _favoriteArticles

    init {
        loadFavoriteArticles()
    }

    private fun loadFavoriteArticles() {
        viewModelScope.launch {
            newsUseCases.getFavoriteArticles().collect { articles ->
                _favoriteArticles.value = articles
            }
        }
    }

    fun removeFromFavorites(articleId: String) {
        viewModelScope.launch {
            newsUseCases.removeArticleFromFavorites(articleId)
            loadFavoriteArticles()
        }
    }
}