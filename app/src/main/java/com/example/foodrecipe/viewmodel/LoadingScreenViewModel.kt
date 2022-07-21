package com.example.foodrecipe.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodrecipe.model.entities.MealsEntity
import com.example.foodrecipe.model.repostitory.meals.MealsRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoadingScreenViewModel(private val mealsRepository: MealsRepository): ViewModel()  {

    private val _meal = MutableLiveData<MealsEntity>()

    val meal:LiveData<MealsEntity>  get() = _meal

    private val _isErrorLoading = MutableLiveData<Boolean>(false)

    val isErrorLoading:LiveData<Boolean> get() = _isErrorLoading

    init {
        viewModelScope.launch {
            delay(300L)
            mealsRepository.listenCurrentRecipe().collect{
                if (it != null)
                    _meal.postValue(it.toMealEntity())
                else
                    _isErrorLoading.postValue(true)
            }
        }
    }

}