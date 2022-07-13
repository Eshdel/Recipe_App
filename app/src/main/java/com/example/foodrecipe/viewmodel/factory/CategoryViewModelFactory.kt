package com.example.foodrecipe.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.foodrecipe.dao.RecipeDao
import com.example.foodrecipe.viewmodel.CategoryDaoViewModel

class CategoryViewModelFactory(private val recipeDao: RecipeDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if(modelClass.isAssignableFrom(CategoryDaoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CategoryDaoViewModel(recipeDao) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }

}
