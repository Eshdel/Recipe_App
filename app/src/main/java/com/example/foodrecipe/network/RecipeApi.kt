package com.example.foodrecipe.network

import com.example.foodrecipe.network.retrofitclient.RetrofitClientInstance
import com.example.foodrecipe.network.service.RecipeApiService

object RecipeApi {
    val retrofitService: RecipeApiService by lazy { RetrofitClientInstance.retrofitInstance!!.create(RecipeApiService::class.java) }
}