package com.chaidar.dicoding.newsdrawersubmission1androiddicodingexpert.presentation.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val newsUseCases: com.chaidar.dicoding.core.domain.usecase.NewsUseCases
) : ViewModel() {

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> get() = _isFavorite

    fun saveArticleToFavorites(article: com.chaidar.dicoding.core.domain.model.Article) {
        viewModelScope.launch {
            newsUseCases.saveArticleToFavorites(article)
        }
    }

    fun checkIfFavorite(articleId: String) {
        viewModelScope.launch {
            val favoriteArticles = newsUseCases.getFavoriteArticles().first()
            _isFavorite.value = favoriteArticles.any { it.id == articleId }
        }
    }

    fun toggleFavoriteStatus(article: com.chaidar.dicoding.core.domain.model.Article) {
        viewModelScope.launch {
            if (_isFavorite.value == true) {
                newsUseCases.removeArticleFromFavorites(article.id)
                _isFavorite.value = false
            } else {
                newsUseCases.saveArticleToFavorites(article)
                _isFavorite.value = true
            }
        }
    }
}