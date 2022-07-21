package com.example.foodrecipe.viewmodel

import androidx.lifecycle.*
import com.example.foodrecipe.model.repostitory.categories.CategoriesRepository
import com.example.foodrecipe.model.repostitory.meals.MealsRepository
import kotlinx.coroutines.launch

class SplashScreenViewModel(categoryRepository: CategoriesRepository,mealsRepository: MealsRepository): BaseViewModel(categoryRepository, mealsRepository) {

    private  val _loadingProgress = MutableLiveData<Int>()

    val loadingProgress:LiveData<Int> get() = _loadingProgress

    init {
        viewModelScope.launch {
            mealsRepository.setCurrentMeals(categoryRepository.getCategories()).collect{
                _loadingProgress.postValue(it)
            }
        }
    }
}
