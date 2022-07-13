package com.example.foodrecipe.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.foodrecipe.dao.RecipeDao
import com.example.foodrecipe.model.ApiStatus
import com.example.foodrecipe.model.entities.CategoryItems
import com.example.foodrecipe.model.entities.Meal
import com.example.foodrecipe.network.RecipeApi
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response

class MealListDaoViewModel(val dao: RecipeDao): BaseDaoViewModel<Meal>() {

    val meal: LiveData<Meal?> get() = data

    private val _category = MutableLiveData("")
    val category:LiveData<String> get() = _category

    fun loadMealFromNetwork(category: String) {

        _status.value = ApiStatus.LOADING

        val call = RecipeApi.retrofitService.getMealList(category)
        viewModelScope.launch { loadData(call) }
    }

    private fun insertMealDataIntoRoomDatabase(categoryName: String, meal: Meal?) {

        viewModelScope.launch {

            if(meal?.mealsItem == dao.getSpecificMealList(categoryName)) return@launch

            dao.clearMealDatabase(categoryName)

            for (item in meal?.mealsItem!!) {
                val mealItemModel =  item.copy(categoryName = categoryName)
                dao.insertMeal(mealItemModel)
            }
        }
    }

    // if we cannot load data from web
   fun loadMealFromDatabase() {

        viewModelScope.launch {
            _data.value = Meal(0, dao.getSpecificMealList(category.value!!))
            _status.value = ApiStatus.DONE
        }

        _status.value = ApiStatus.NONE
    }

    fun loadMealFromNetwork(letter:Char) {

        val call = RecipeApi.retrofitService.getMealListByFirstLetter(letter.toString())
        viewModelScope.launch {loadData(call)}
    }

    fun changeCategory(category:CategoryItems) {
        _category.value = category.strcategory
    }

    fun changeCategory(newCategory: String) {
        _category.value = newCategory
    }

    override fun Response(call: Call<Meal>, response: Response<Meal>) {
        insertMealDataIntoRoomDatabase(category.value!!,response.body())
    }

    override fun Failure(call: Call<Meal>, t: Throwable) {
        loadMealFromDatabase()
    }
}