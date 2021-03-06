package com.example.foodrecipe.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.foodrecipe.model.entities.CategoryItems
import com.example.foodrecipe.model.entities.MealsEntity
import com.example.foodrecipe.model.entities.MealsItems
import com.example.foodrecipe.model.repostitory.categories.CategoriesRepository
import com.example.foodrecipe.model.repostitory.meals.MealsRepository
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import java.lang.NullPointerException


class HomeScreenViewModel(categoryRepository: CategoriesRepository, mealsRepository: MealsRepository): BaseViewModel(categoryRepository, mealsRepository) {

    private val searchStringFlow = MutableSharedFlow<String?>(0,1,BufferOverflow.DROP_OLDEST)

    private var searchString:String? = null

    private val _currentMealsList = MutableLiveData<List<MealsItems>?>()

    val currentMealsList:LiveData<List<MealsItems>?> get() = _currentMealsList

    private  val _currentCategory = MutableLiveData<CategoryItems?>()

    val currentCategory:LiveData<CategoryItems?> get() = _currentCategory

    private val _currentCategories = MutableLiveData<List<CategoryItems>?>()

    val currentCategories:LiveData<List<CategoryItems>?> get() = _currentCategories

    private val _currentMeal = MutableLiveData<MealsEntity?>()

    val currentMeal:LiveData<MealsEntity?> get() = _currentMeal

    init {
        viewModelScope.launch {
            _currentCategories.postValue(categoryRepository.getCategories())
        }

        viewModelScope.launch{
            mealsRepository.setCurrentMeals(categoryRepository.getCurrentCategory(), searchString)
        }

        viewModelScope.launch {
            mealsRepository.listenCurrentMeals().collect {
                _currentMealsList.postValue(it)
                for(el in it) {
                    Log.e("List", el.strMeal.toString())
                }
            }
        }

        viewModelScope.launch {
            categoryRepository.listenCurrentCategory().collect {
                mealsRepository.setCurrentMeals(it,searchString)
                _currentCategory.postValue(it)
            }
        }

        viewModelScope.launch {
            mealsRepository.listenCurrentRecipe().collect {
                if (it != null) {
                    _currentMeal.value = it.toMealEntity()
                }
                else {
                    _currentMeal.value = null
                }
            }
        }

        viewModelScope.launch {
            searchStringFlow.collect{
                searchString = it
                _currentCategory.value = null
                mealsRepository.setCurrentMeals(it)
            }
        }
    }

    fun selectCategory(category: CategoryItems) {
        categoryRepository.setCurrentCategory(category)
    }

    fun unselectCategory() {
        viewModelScope.launch{
            categoryRepository.setCurrentCategory(null)
            mealsRepository.setCurrentMeals(null,searchString)
        }
    }

    fun setSearchString(searchString:String?) {
        this.searchStringFlow.tryEmit(searchString)
    }

    fun setCurrentMeal(meal:MealsItems) {
        viewModelScope.launch {
            mealsRepository.setCurrentRecipe(meal.idMeal!!.toInt())
        }
    }

    fun showAllMeals(){
        viewModelScope.launch {
            mealsRepository.setCurrentMeals()
        }
    }
    fun syncData(){
        viewModelScope.launch {
            _currentCategories.postValue(categoryRepository.getCategories())
            mealsRepository.setCurrentMeals(categoryRepository.getCategories()).collect{}
            mealsRepository.setCurrentMeals()
        }
    }

    fun syncData(category: CategoryItems?){
        if (category != null)
            viewModelScope.launch {
                mealsRepository.reloadMeals(category)
            }
        else
            syncData()
    }

    override fun onCleared() {
        super.onCleared()
    }
}