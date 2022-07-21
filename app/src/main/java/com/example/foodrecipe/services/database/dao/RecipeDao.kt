package com.example.foodrecipe.services.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.foodrecipe.model.entities.CategoryItems
import com.example.foodrecipe.model.entities.MealsItems
import com.example.foodrecipe.model.entities.Recipes


@Dao
interface RecipeDao {

    @Query("SELECT * FROM categoryitems ORDER BY strcategory")
    suspend fun getAllCategory() : List<CategoryItems>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(categoryItems: CategoryItems?)

    @Query("DELETE FROM categoryitems")
    suspend fun clearCategoryDatabase()

    @Query("SELECT * FROM MealItems WHERE categoryName =:categoryName  ORDER BY id DESC")
    suspend fun getSpecificMealList(categoryName:String) : List<MealsItems>

    @Query("SELECT * FROM MealItems ORDER BY strmeal")
    suspend fun getSpecificMealList(): List<MealsItems>

    @Query("DELETE FROM mealitems")
    suspend fun clearMealDatabase()

    @Query("DELETE FROM mealitems where categoryName =:categoryName")
    suspend fun clearMealDatabase(categoryName:String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeal(mealsItems: MealsItems?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipe(recipe: Recipes)

    @Query("SELECT * FROM recipes where dish_id = :id")
    suspend fun getRecipe(id:String): Recipes?

    @Query("SELECT * FROM recipes")
    suspend fun getRecipes():List<Recipes>?
}