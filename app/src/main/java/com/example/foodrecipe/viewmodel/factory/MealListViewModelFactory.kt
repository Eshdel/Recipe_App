package com.example.foodrecipe.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.foodrecipe.dao.RecipeDao
import com.example.foodrecipe.viewmodel.MealListDaoViewModel

class MealListViewModelFactory(private val recipeDao:RecipeDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(MealListDaoViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return MealListDaoViewModel(recipeDao) as T
    }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}