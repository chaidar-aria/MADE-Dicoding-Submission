package com.chaidar.dicoding.core.domain.usecase

import com.chaidar.dicoding.core.domain.model.Article
import com.chaidar.dicoding.core.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NewsUseCases @Inject constructor(
    private val repository: NewsRepository
) {
    fun getNewsList(query: String, apiKey: String): Flow<List<Article>> {
        return repository.getNewsList(query, apiKey)
    }

    fun getFavoriteArticles(): Flow<List<Article>> {
        return repository.getFavoriteArticles()
    }

    suspend fun saveArticleToFavorites(article: Article) {
        repository.saveArticleToFavorites(article)
    }

    suspend fun removeArticleFromFavorites(articleId: String) {
        repository.removeArticleFromFavorites(articleId)
    }
}