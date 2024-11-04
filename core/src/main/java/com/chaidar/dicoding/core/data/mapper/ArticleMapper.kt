package com.chaidar.dicoding.core.data.mapper

import com.chaidar.dicoding.core.data.model.ArticleResponse
import com.chaidar.dicoding.core.data.source.local.ArticleEntity


object ArticleMapper {

    fun mapToDomain(articleResponse: ArticleResponse): com.chaidar.dicoding.core.domain.model.Article {
        return com.chaidar.dicoding.core.domain.model.Article(
            id = articleResponse.url,  // Gunakan URL sebagai ID unik
            title = articleResponse.title,
            description = articleResponse.description,
            url = articleResponse.url,
            urlToImage = articleResponse.urlToImage,
            publishedAt = articleResponse.publishedAt,
            sourceName = articleResponse.source?.name ?: "Unknown"
        )
    }

    fun mapToDomain(articleEntity: ArticleEntity): com.chaidar.dicoding.core.domain.model.Article {
        return com.chaidar.dicoding.core.domain.model.Article(
            id = articleEntity.id,
            title = articleEntity.title,
            description = articleEntity.description,
            url = articleEntity.url,
            urlToImage = articleEntity.urlToImage,
            publishedAt = articleEntity.publishedAt,
            sourceName = articleEntity.sourceName
        )
    }

    fun mapToEntity(article: com.chaidar.dicoding.core.domain.model.Article): ArticleEntity {
        return ArticleEntity(
            id = article.id,
            title = article.title,
            description = article.description,
            url = article.url,
            urlToImage = article.urlToImage,
            publishedAt = article.publishedAt,
            sourceName = article.sourceName
        )
    }
}
