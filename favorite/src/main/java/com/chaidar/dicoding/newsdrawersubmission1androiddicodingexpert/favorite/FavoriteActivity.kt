package com.chaidar.dicoding.newsdrawersubmission1androiddicodingexpert.favorite

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.chaidar.dicoding.favorite.R

class FavoriteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_favorite)

        supportFragmentManager.beginTransaction()
            .replace(android.R.id.content, FavoriteFragment())
            .commit()
    }
}