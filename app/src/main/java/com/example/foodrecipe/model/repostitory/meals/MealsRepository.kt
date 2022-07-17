package com.example.foodrecipe.model.repostitory.meals

import com.example.foodrecipe.model.entities.CategoryItems
import com.example.foodrecipe.model.entities.MealsEntity
import com.example.foodrecipe.model.entities.MealsItems
import kotlinx.coroutines.flow.Flow


interface MealsRepository {

    suspend fun setCurrentMeals(category: CategoryItems?)

    suspend fun setCurrentMeals(category: CategoryItems?, filterString: String?)

    suspend fun setCurrentMeals(categories: List<CategoryItems>): Flow<Int>

    suspend fun setCurrentMeals()

    suspend fun setCurrentMeals(filterString: String?)

    suspend fun setAvailableCurrentMeals(availableMeals:List<MealsEntity>)

    suspend fun setCurrentMeal(id:Int)

    fun getCurrentMeal():MealsEntity?

    fun setCurrentMeal(meal: MealsEntity?)

    fun listenCurrentMeals(): Flow<List<MealsItems>>

    fun listenCurrentMeal(): Flow<MealsEntity?>

}

typealias MealItemToMealsEntity = (MealsItems) -> MealsEntity
typealias MealsEntityToMealItem = (MealsEntity) -> MealsItems