package com.chaidar.dicoding.newsdrawersubmission1androiddicodingexpert.favorite.di

import android.content.Context
import com.chaidar.dicoding.newsdrawersubmission1androiddicodingexpert.favorite.FavoriteFragment
import com.chaidar.dicoding.newsdrawersubmission1androiddicodingexpert.presentation.di.AppModule
import dagger.BindsInstance
import dagger.Component

@Component(dependencies = [AppModule.FavoriteModuleDependencies::class])
interface FavoriteComponent {

    fun inject(favoriteFragment: FavoriteFragment)

    @Component.Builder
    interface Builder {
        fun context(@BindsInstance context: Context): Builder
        fun appDependencies(favoriteModuleDependencies: AppModule.FavoriteModuleDependencies): Builder
        fun build(): FavoriteComponent
    }
}