package com.example.foodrecipe

import android.app.Application
import com.example.foodrecipe.model.repostitory.categories.InMemoryCategoriesRepository
import com.example.foodrecipe.model.repostitory.meals.InMemoryMealsRepository

class App:Application() {
    val mealsRepository by lazy {InMemoryMealsRepository(this.applicationContext)}

    val categoryRepository by lazy {InMemoryCategoriesRepository(this.applicationContext)}
}
