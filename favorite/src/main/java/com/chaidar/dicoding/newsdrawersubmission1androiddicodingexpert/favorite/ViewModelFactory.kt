package com.chaidar.dicoding.newsdrawersubmission1androiddicodingexpert.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.chaidar.dicoding.core.domain.usecase.NewsUseCases
import javax.inject.Inject

class ViewModelFactory @Inject constructor(
    private val newsUseCase: NewsUseCases
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(FavoriteViewModel::class.java) -> FavoriteViewModel(
                newsUseCase
            ) as T

            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}