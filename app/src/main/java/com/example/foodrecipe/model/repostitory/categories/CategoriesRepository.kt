package com.example.foodrecipe.model.repostitory.categories

import com.example.foodrecipe.model.entities.CategoryItems
import kotlinx.coroutines.flow.Flow

interface CategoriesRepository {

    suspend fun getCategories(): List<CategoryItems>

    fun setCurrentCategory(category: CategoryItems?)

    fun getCurrentCategory():CategoryItems?

    fun listenCurrentCategory(): Flow<CategoryItems?>
}