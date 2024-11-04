package com.chaidar.dicoding.core.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.chaidar.dicoding.core.data.source.local.ArticleEntity
import com.chaidar.dicoding.core.data.source.local.NewsDao

@Database(entities = [ArticleEntity::class], version = 1, exportSchema = false)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsDao
}
