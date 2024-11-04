package com.chaidar.dicoding.core.data.source.remote

import android.util.Log
import com.chaidar.dicoding.core.data.model.NewsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NewsRemoteDataSource @Inject constructor(
    private val apiService: NewsApiService
) {
    suspend fun fetchNews(query: String, apiKey: String): Result<NewsResponse> {
        return withContext(Dispatchers.IO) {
            try {
                Log.d(
                    "NewsRemoteDataSource",
                    "Fetching news with query: $query and apiKey: $apiKey"
                )

                val response = apiService.getNewsList(query, apiKey)
                if (response.isSuccessful) {
                    response.body()?.let {
                        Log.d("NewsRemoteDataSource", "News fetched successfully: $it")
                        Result.success(it)
                    } ?: run {
                        Log.e("NewsRemoteDataSource", "Empty body in response")
                        Result.failure(Exception("Empty body"))
                    }
                } else {
                    Log.e("NewsRemoteDataSource", "Failed response with code: ${response.code()}")
                    Result.failure(Exception("Failed response with code: ${response.code()}"))
                }
            } catch (e: Exception) {
                Log.e("NewsRemoteDataSource", "Exception occurred: ${e.message}", e)
                Result.failure(e)
            }
        }
    }
}