package com.example.foodrecipe.model.repostitory.meals

import android.content.Context
import android.util.Log
import com.example.foodrecipe.services.database.dao.RecipeDao
import com.example.foodrecipe.services.database.RecipeDatabase
import com.example.foodrecipe.model.entities.CategoryItems
import com.example.foodrecipe.model.entities.MealsItems
import com.example.foodrecipe.model.entities.Recipes
import com.example.foodrecipe.services.network.api.RecipeApi
import com.example.foodrecipe.services.network.api.RecipeApiService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.awaitResponse
import kotlin.NullPointerException


val MAX_PROGRESS = 100

object InMemoryMealsRepository : MealsRepository {

    private lateinit var dao: RecipeDao

    private lateinit var recipeApiService: RecipeApiService

    private lateinit var dispatcher: CoroutineDispatcher

    private val categoryMeals: MutableMap<String,List<MealsItems>> = mutableMapOf()

    private var allMeals: MutableSet<MealsItems> = mutableSetOf()

    private var currentMeal: Recipes? = null

    private val currentMealsFlow = MutableSharedFlow<List<MealsItems>>(replay = 0, extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    private val currentMealFlow = MutableSharedFlow<Recipes?>(replay = 1, extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    private suspend fun getMealsFromServer(category: CategoryItems, filter: (List<MealsItems>) -> List<MealsItems> = {it}) : List<MealsItems>{
        recipeApiService.getMealList(category.strcategory!!).awaitResponse().body().let {
            if (it == null || it.mealsItem.isNullOrEmpty())
                throw NullPointerException("Error Loading Data From Server !")

            it.mealsItem!!.forEach{it.categoryName = category.strcategory}

            insertMealsIntoDataBase(category, it.mealsItem!!)

            return filter(it.mealsItem!!)
        }
    }

    private suspend fun getMealsFromDataBase(category: CategoryItems, filter: (List<MealsItems>) -> List<MealsItems> = {it}) : List<MealsItems> {
        return filter(dao.getSpecificMealList(category.strcategory!!))
    }

    private suspend fun getMealsFromDataBase(filter: (List<MealsItems>) -> List<MealsItems> = {it}):List<MealsItems> {
        return filter(dao.getSpecificMealList())
    }

    private suspend fun insertMealsIntoDataBase(category: CategoryItems?,meals:List<MealsItems>) {
        if (category == null) {
            insertMealsIntoDataBase(meals)
            return
        }

        dao.clearMealDatabase(category.strcategory!!)
        for (meal in meals)
            dao.insertMeal(meal)

    }

    private suspend fun insertMealsIntoDataBase(meals:List<MealsItems>) {
        dao.clearMealDatabase()
        for (meal in meals)
            dao.insertMeal(meal)
    }

    private suspend fun insertRecipeIntoDataBase(recipe: Recipes) {
        if (dao.getRecipes()!!.find { it.dishName == recipe.dishName} == null)
            dao.insertRecipe(recipe)
    }

    override suspend fun setCurrentMeals(category: CategoryItems?) {
        if (category == null){
            setCurrentMeals()
            return
        }

        if (categoryMeals.get(category.strcategory).isNullOrEmpty()) {
            try {
                addMeals(getMealsFromServer(category),category)
            }
            catch (e:Exception) {
                addMeals(getAvaliableMealList(dao.getRecipes()!!,category))
            }

            return
        }

        categoryMeals[category.strcategory].let { currentMealsFlow.emit(it!!)}
    }

    private fun addMeals(meals:List<MealsItems>, category: CategoryItems,filter: (List<MealsItems>) -> List<MealsItems> = {it}) {
        currentMealsFlow.tryEmit(filter(meals))
        categoryMeals.put(category.strcategory!!,meals)
        allMeals.addAll(meals)
    }

    private fun addMeals(meals:List<MealsItems>,filter: (List<MealsItems>) -> List<MealsItems> = {it}) {
        currentMealsFlow.tryEmit(filter(meals))
        allMeals.addAll(meals)
    }

    private fun setMeals(meals:List<MealsItems>,filter: (List<MealsItems>) -> List<MealsItems> = {it}) {
        allMeals = meals.toMutableSet()
        currentMealsFlow.tryEmit(filter(meals))
    }

    override suspend fun setCurrentMeals(category: CategoryItems?, filterString: String?) {
        if (category == null) {
            setCurrentMeals(filterString)
            return
        }

        if (filterString.isNullOrBlank()){
            setCurrentMeals(category)
            return
        }

        if (categoryMeals[category.strcategory] == null) {
            try {
                getMealsFromServer(category){it.filter{it.strMeal!!.isContainsLowerCase(filterString)}}
            }

            catch (e:Exception) {
                addMeals(getAvaliableMealList(dao.getRecipes()!!,category)){it.filter{it.strMeal!!.isContainsLowerCase(filterString)}}
            }

            return
        }

        currentMealsFlow.emit(categoryMeals[category.strcategory]!!.filter{it.strMeal!!.isContainsLowerCase(filterString)})}

    override suspend fun setCurrentMeals(categories: List<CategoryItems>) = flow{
        var progress = 0.0

        try {
            for (category in categories) {
                if(categoryMeals[category.strcategory] == null)
                    addMeals(getMealsFromServer(category),category)

                progress += ((1.0 / categories.size) * 100).toInt()

                emit(progress.toInt())
            }
        }
        catch (e:Exception) {
            setMeals(getAvaliableMealList(dao.getRecipes()!!,null))
        }

        emit(MAX_PROGRESS)

    }.flowOn(dispatcher)

    override suspend fun setCurrentMeals() {
        if(allMeals.toList().isEmpty()) {
            setMeals(getAvaliableMealList(dao.getRecipes()!!,null))
            return
        }
        setMeals(allMeals.toList())
    }

    override suspend fun setCurrentMeals(filterString: String?){
        if (filterString.isNullOrBlank()) {
            setCurrentMeals()
            return
        }

        if(allMeals.toList().isEmpty()) {
            setMeals(getMealsFromDataBase({it.filter{ it.strMeal!!.isContainsLowerCase(filterString)}}))
            return
        }

        setMeals(allMeals.toList(),{it.filter{ it.strMeal!!.isContainsLowerCase(filterString)}})
    }

    override suspend fun reloadMeals(category: CategoryItems) {
        try {
            addMeals(getMealsFromServer(category),category)
        }
        catch (e:Exception) {
            setMeals(getAvaliableMealList(dao.getRecipes()!!,category))
        }
    }

    override suspend fun getAvaliableMealList(recipes: List<Recipes>,category: CategoryItems?):List<MealsItems>{
        var mealListFromDatabase: List<MealsItems>?

        if (category == null)
            mealListFromDatabase = getMealsFromDataBase{it.filter{ meal -> recipes.find{it.dishName == meal.strMeal} != null}}
        else
            mealListFromDatabase = getMealsFromDataBase(category,{it.filter{ meal -> recipes.find{it.dishName == meal.strMeal} != null}})

        val avaliableMealList = mealListFromDatabase.filter { meal -> recipes.find{it.dishName == meal.strMeal} != null}

        return avaliableMealList
    }

    override fun getCurrentRecipe(): Recipes? = currentMeal

    override fun setCurrentRecipe(recipe: Recipes?) {
        currentMeal = recipe
        currentMealFlow.tryEmit(recipe)
    }

    override suspend fun setCurrentRecipe(id: Int) {
        try {
            recipeApiService.getSpecificItem(id.toString()).awaitResponse().body()!!.let {
                insertRecipeIntoDataBase(it.mealsEntity.first().toRecipes())
                setCurrentRecipe(it.mealsEntity.first().toRecipes())
            }
        }
        catch (e:Exception) {
            val recipe = dao.getRecipe(id.toString())
            setCurrentRecipe(recipe)
        }
    }

    override fun listenCurrentMeals(): Flow<List<MealsItems>> = currentMealsFlow

    override fun listenCurrentRecipe(): Flow<Recipes?> = currentMealFlow

    operator fun invoke(context: Context,dispatcher: CoroutineDispatcher = Dispatchers.IO): MealsRepository {
        dao = RecipeDatabase.getDatabase(context).recipeDao()
        recipeApiService = RecipeApi.retrofitService
        this.dispatcher = dispatcher

        return this
    }
}

fun String.isContainsLowerCase(string: String):Boolean {
    return this.lowercase().contains(string.lowercase())
}