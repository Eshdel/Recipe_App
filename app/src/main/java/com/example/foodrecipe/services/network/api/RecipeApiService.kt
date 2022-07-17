package com.example.foodrecipe.services.network.api

import com.example.foodrecipe.model.entities.Category
import com.example.foodrecipe.model.entities.Meal
import com.example.foodrecipe.model.entities.MealResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RecipeApiService {

    @GET("categories.php")
    fun getCategoryList() : Call<Category>

    @GET("filter.php")
    fun getMealList(@Query("c") category: String):Call<Meal>

    @GET("lookup.php")
    fun getSpecificItem(@Query("i") id:String):Call<MealResponse>

    @GET("search.php")
    fun getMealListByFirstLetter(@Query("f") letter: String):Call<Meal>
}