package com.example.foodrecipe.viewmodel

import androidx.lifecycle.*
import com.example.foodrecipe.dao.RecipeDao
import com.example.foodrecipe.model.ApiStatus
import com.example.foodrecipe.model.entities.Category
import com.example.foodrecipe.network.RecipeApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.*


class CategoryDaoViewModel(val dao:RecipeDao):BaseDaoViewModel<Category>() {

    val category:LiveData<Category?> get() = _data
    private  var isFirstRequest = true
    fun loadCategoryFromNetwork() {

        _status.value = ApiStatus.LOADING

        val call = RecipeApi.retrofitService.getCategoryList()

        viewModelScope.launch {
            if (!isFirstRequest) delay(1000L)
                loadData(call)

            isFirstRequest = false
        }

    }

    private fun insertCategoryToDatabase() {

        viewModelScope.launch {
            dao.clearDatabase()
            for(item in _data.value?.categorieitems!!) {
                dao.insertCategory(item)
            }
        }
    }

    fun loadCategoryFromDatabase() {

        _status.value = ApiStatus.LOADING

        viewModelScope.launch {
            _data.value = Category(0, dao.getAllCategory())
            _status.value = ApiStatus.DONE
        }
    }

    override fun Response(call: Call<Category>, response: Response<Category>) {
        insertCategoryToDatabase()
    }

    override fun Failure(call: Call<Category>, t: Throwable) {
        loadCategoryFromDatabase()
    }
}
