package com.example.foodrecipe.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.foodrecipe.App
import com.example.foodrecipe.model.entities.MealsEntity
import com.example.foodrecipe.model.repostitory.meals.MealsRepository
import com.example.foodrecipe.viewmodel.DetailScreenViewModel

class DetailScreenViewModelFactory(val meal: MealsEntity,private val app: App):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailScreenViewModel::class.java))
            return DetailScreenViewModel(meal,app.mealsRepository) as T

        else
            throw IllegalArgumentException("Unknown ViewModel class")
    }
}