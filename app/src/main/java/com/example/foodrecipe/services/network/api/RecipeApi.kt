package com.example.foodrecipe.services.network.api

import com.example.foodrecipe.services.network.retrofitclient.RetrofitClientInstance

object RecipeApi {
    val retrofitService: RecipeApiService by lazy { RetrofitClientInstance.retrofitInstance!!.create(RecipeApiService::class.java) }
}