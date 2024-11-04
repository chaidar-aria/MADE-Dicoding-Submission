package com.chaidar.dicoding.core.data.repository

import com.chaidar.dicoding.core.data.mapper.ArticleMapper
import com.chaidar.dicoding.core.data.source.local.NewsDao
import com.chaidar.dicoding.core.data.source.remote.NewsRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val newsRemoteDataSource: NewsRemoteDataSource,
    private val newsDao: NewsDao
) : com.chaidar.dicoding.core.domain.repository.NewsRepository {

    override fun getNewsList(query: String, apiKey: String): Flow<List<com.chaidar.dicoding.core.domain.model.Article>> = flow {
        val response = newsRemoteDataSource.fetchNews(query, apiKey)
        if (response.isSuccess) {
            response.getOrNull()?.articles?.let { articles ->
                emit(articles.map { ArticleMapper.mapToDomain(it) })
            }
        } else {
            throw response.exceptionOrNull() ?: Exception("Unknown error")
        }
    }

    override fun getFavoriteArticles(): Flow<List<com.chaidar.dicoding.core.domain.model.Article>> {
        return newsDao.getFavoriteArticles().map { entities ->
            entities.map { ArticleMapper.mapToDomain(it) }
        }
    }

    override suspend fun saveArticleToFavorites(article: com.chaidar.dicoding.core.domain.model.Article) {
        newsDao.insertArticle(ArticleMapper.mapToEntity(article))
    }

    override suspend fun removeArticleFromFavorites(articleId: String) {
        newsDao.deleteArticleById(articleId)
    }
}