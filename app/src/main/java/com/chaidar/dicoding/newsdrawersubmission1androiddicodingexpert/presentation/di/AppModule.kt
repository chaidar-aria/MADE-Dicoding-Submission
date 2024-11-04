package com.chaidar.dicoding.newsdrawersubmission1androiddicodingexpert.presentation.di

import android.content.Context
import androidx.room.Room
import com.chaidar.dicoding.core.data.database.NewsDatabase
import com.chaidar.dicoding.core.data.repository.NewsRepositoryImpl
import com.chaidar.dicoding.core.data.source.local.NewsDao
import com.chaidar.dicoding.core.data.source.remote.NewsApiService
import com.chaidar.dicoding.core.data.source.remote.NewsRemoteDataSource
import com.chaidar.dicoding.core.domain.repository.NewsRepository
import com.chaidar.dicoding.core.domain.usecase.NewsUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        val hostname = "newsapi.org"
        val certificatePinner = CertificatePinner.Builder()
            .add(hostname, "sha256/p/RZhoZj/bpjprMDMoNVQ04tZgIX0kd39gI9NxT6w8k=")
            .add(hostname, "sha256/o3acJR7JExJvqoEhsElholZyQx4woNrA5d3b3DDQgh8=")
            .build()

        val client = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .certificatePinner(certificatePinner)
            .build()

        return Retrofit.Builder()
            .baseUrl("https://$hostname")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideNewsApiService(retrofit: Retrofit): NewsApiService {
        return retrofit.create(NewsApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): NewsDatabase {
        val passphrase: ByteArray = SQLiteDatabase.getBytes("newsapp".toCharArray())
        val factory = SupportFactory(passphrase)

        return Room.databaseBuilder(
            context,
            NewsDatabase::class.java,
            "news_db"
        )
            .openHelperFactory(factory)
            .build()
    }

    @Provides
    fun provideNewsDao(database: NewsDatabase) = database.newsDao()

    @Provides
    @Singleton
    fun provideNewsRepository(
        remoteDataSource: NewsRemoteDataSource,
        newsDao: NewsDao
    ): NewsRepository {
        return NewsRepositoryImpl(
            remoteDataSource,
            newsDao
        )
    }

    @Provides
    @Singleton
    fun provideNewsUseCases(repository: NewsRepository): NewsUseCases {
        return NewsUseCases(repository)
    }

    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface FavoriteModuleDependencies {
        fun newsUseCase(): NewsUseCases
    }
}