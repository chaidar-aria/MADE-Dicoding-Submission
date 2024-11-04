package com.chaidar.dicoding.core.domain.repository

import com.chaidar.dicoding.core.domain.model.Article
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun getNewsList(query: String, apiKey: String): Flow<List<Article>>

    fun getFavoriteArticles(): Flow<List<Article>>

    suspend fun saveArticleToFavorites(article: Article)

    suspend fun removeArticleFromFavorites(articleId: String)
}