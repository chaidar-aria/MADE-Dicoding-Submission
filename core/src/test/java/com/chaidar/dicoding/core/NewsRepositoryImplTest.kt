package com.chaidar.dicoding.core

import com.chaidar.dicoding.core.data.model.ArticleResponse
import com.chaidar.dicoding.core.data.model.NewsResponse
import com.chaidar.dicoding.core.data.repository.NewsRepositoryImpl
import com.chaidar.dicoding.core.data.source.local.NewsDao
import com.chaidar.dicoding.core.data.source.remote.NewsRemoteDataSource
import com.chaidar.dicoding.core.domain.model.Article
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class NewsRepositoryImplTest {

    @Mock
    private lateinit var remoteDataSource: NewsRemoteDataSource

    @Mock
    private lateinit var newsDao: NewsDao

    private lateinit var repository: NewsRepositoryImpl

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        repository = NewsRepositoryImpl(remoteDataSource, newsDao)
    }

    @Test
    fun `getNewsList should return news from remote data source`() = runBlocking {
        val dummyArticleResponse = ArticleResponse(null, "Author", "Title", "Description", "URL", "URL to Image", "2022-01-01", "Content")
        val dummyNewsResponse = NewsResponse("ok", 1, listOf(dummyArticleResponse))

        `when`(remoteDataSource.fetchNews("jokowi", "API_KEY")).thenReturn(Result.success(dummyNewsResponse))

        val expectedArticles = listOf(
            Article("URL", "Title", "Description", "URL", "URL to Image", "2022-01-01", "Unknown")
        )

        val flow = repository.getNewsList("jokowi", "API_KEY")
        flow.collect { articles ->
            assertEquals(expectedArticles, articles)
        }
    }
}
