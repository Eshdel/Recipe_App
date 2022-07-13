package com.example.foodrecipe.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.foodrecipe.model.entities.MealResponse
import com.example.foodrecipe.viewmodel.DetailScreenViewModel

class DetailScreenViewModelFactory(val mealResponse: MealResponse) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if(modelClass.isAssignableFrom(DetailScreenViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DetailScreenViewModel(mealResponse) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }

}
