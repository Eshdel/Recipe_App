package com.example.foodrecipe.viewmodel

import androidx.lifecycle.ViewModel
import com.example.foodrecipe.model.repostitory.categories.CategoriesRepository
import com.example.foodrecipe.model.repostitory.meals.MealsRepository

open class BaseViewModel(protected val categoryRepository: CategoriesRepository,protected val mealsRepository: MealsRepository):ViewModel()