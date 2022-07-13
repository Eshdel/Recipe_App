package com.example.foodrecipe.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.foodrecipe.dao.RecipeDao
import com.example.foodrecipe.model.ApiStatus
import com.example.foodrecipe.model.entities.MealResponse
import com.example.foodrecipe.network.RecipeApi
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response

class MealDaoViewModel(val dao:RecipeDao) : BaseDaoViewModel<MealResponse>() {
    
    val mealResponse: LiveData<MealResponse?> get() = data

    val meal get() = mealResponse.value?.mealsEntity?.first()


    fun loadMealFromNetwork(id:Int){

        val call = RecipeApi.retrofitService.getSpecificItem(id.toString())
        viewModelScope.launch { loadData(call) }
    }

    override fun Response(call: Call<MealResponse>, response: Response<MealResponse>) {
    }

    override fun Failure(call: Call<MealResponse>, t: Throwable) {

    }
}