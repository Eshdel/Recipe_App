package com.example.foodrecipe.model.repostitory.meals

import com.example.foodrecipe.model.entities.CategoryItems
import com.example.foodrecipe.model.entities.MealsEntity
import com.example.foodrecipe.model.entities.MealsItems
import com.example.foodrecipe.model.entities.Recipes
import kotlinx.coroutines.flow.Flow

interface MealsRepository {

    suspend fun setCurrentMeals(category: CategoryItems?)

    suspend fun reloadMeals(category: CategoryItems)

    suspend fun setCurrentMeals(category: CategoryItems?, filterString: String?)

    suspend fun setCurrentMeals(categories: List<CategoryItems>): Flow<Int>

    suspend fun setCurrentMeals()

    suspend fun setCurrentMeals(filterString: String?)

    suspend fun getAvaliableMealList(availableMeals:List<Recipes>,category: CategoryItems?):List<MealsItems>

    suspend fun setCurrentRecipe(id:Int)

    fun getCurrentRecipe(): Recipes?

    fun setCurrentRecipe(recipe: Recipes?)

    fun listenCurrentMeals(): Flow<List<MealsItems>>

    fun listenCurrentRecipe(): Flow<Recipes?>

}

typealias MealItemToMealsEntity = (MealsItems) -> MealsEntity
typealias MealsEntityToMealItem = (MealsEntity) -> MealsItems