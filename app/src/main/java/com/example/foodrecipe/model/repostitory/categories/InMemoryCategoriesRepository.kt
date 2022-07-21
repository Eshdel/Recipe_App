package com.example.foodrecipe.model.repostitory.categories

import android.content.Context
import android.util.Log
import com.example.foodrecipe.services.database.dao.RecipeDao
import com.example.foodrecipe.services.database.RecipeDatabase
import com.example.foodrecipe.model.entities.CategoryItems
import com.example.foodrecipe.services.network.api.RecipeApi
import com.example.foodrecipe.services.network.api.RecipeApiService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.withContext
import retrofit2.awaitResponse
import java.lang.NullPointerException
import kotlin.math.log

object InMemoryCategoriesRepository : CategoriesRepository {

    private lateinit var dao: RecipeDao
    private lateinit var recipeApiService: RecipeApiService
    private lateinit var dispatcher: CoroutineDispatcher

    private var categories:List<CategoryItems> = listOf()

    private var currentCategory:CategoryItems? = null

    private val currentCategoryFlow = MutableSharedFlow<CategoryItems?>(replay = 0, extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    private suspend fun getCategoriesFromServer():List<CategoryItems> {
            try {
            recipeApiService.getCategoryList().awaitResponse().body().let {
                categories = it!!.categorieitems!!
                insertCategoriesIntoDatabase(categories)
                return categories }
            }

            catch (e:Exception) {
                return getCategoriesFromDatabase()
            }

    }

    private suspend fun getCategoriesFromDatabase():List<CategoryItems> {

        dao.getAllCategory().let {
            categories = it
            return categories
        }
    }

    private suspend fun insertCategoriesIntoDatabase(categories:List<CategoryItems>) {
        withContext(dispatcher) {
            dao.clearCategoryDatabase()

            for (category in categories)
                dao.insertCategory(category)
        }

    }

    override suspend fun getCategories(): List<CategoryItems> {
        if(categories.isEmpty()) {
            return getCategoriesFromServer()
        }

        return categories
    }

    override fun setCurrentCategory(category: CategoryItems?) {
        currentCategory = category
        currentCategoryFlow.tryEmit(currentCategory)
    }

    override fun getCurrentCategory(): CategoryItems? = currentCategory

    override fun listenCurrentCategory(): Flow<CategoryItems?> = currentCategoryFlow

    operator fun invoke(context: Context,dispatcher: CoroutineDispatcher = Dispatchers.IO): CategoriesRepository {
        dao = RecipeDatabase.getDatabase(context).recipeDao()
        recipeApiService = RecipeApi.retrofitService
        InMemoryCategoriesRepository.dispatcher = dispatcher

        return this
    }
}