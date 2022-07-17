package com.example.foodrecipe.viewmodel.factory

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.foodrecipe.App
import com.example.foodrecipe.viewmodel.HomeScreenViewModel
import com.example.foodrecipe.viewmodel.SplashScreenViewModel

typealias ViewModelCreator = (App) -> ViewModel?

class BaseViewModelFactory(private val app:App,  private val viewModelCreator: ViewModelCreator = {null}):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val viewModel = when(modelClass) {
            SplashScreenViewModel::class.java -> SplashScreenViewModel(app.categoryRepository,app.mealsRepository)
            HomeScreenViewModel::class.java -> HomeScreenViewModel(app.categoryRepository,app.mealsRepository)

            else -> viewModelCreator(app) ?: throw IllegalArgumentException("Unknown ViewModel class")
        }

        return viewModel as T
    }
}

inline fun <reified VM:ViewModel> Fragment.baseViewModels(noinline creator:ViewModelCreator = {null}):Lazy<VM> {
    return viewModels{BaseViewModelFactory(requireContext().applicationContext as App, creator)}
}