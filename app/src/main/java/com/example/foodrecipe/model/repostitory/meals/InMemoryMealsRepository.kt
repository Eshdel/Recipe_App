package com.example.foodrecipe.model.repostitory.meals

import android.content.Context
import android.content.IntentFilter
import android.util.Log
import com.example.foodrecipe.services.database.dao.RecipeDao
import com.example.foodrecipe.services.database.RecipeDatabase
import com.example.foodrecipe.model.entities.CategoryItems
import com.example.foodrecipe.model.entities.MealsEntity
import com.example.foodrecipe.model.entities.MealsItems
import com.example.foodrecipe.services.network.api.RecipeApi
import com.example.foodrecipe.services.network.api.RecipeApiService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import retrofit2.awaitResponse


val MAX_PROGRESS = 100
object InMemoryMealsRepository : MealsRepository {

    private lateinit var dao: RecipeDao

    private lateinit var recipeApiService: RecipeApiService

    private lateinit var dispatcher: CoroutineDispatcher

    private val categoryMeals: MutableMap<String,List<MealsItems>> = mutableMapOf()

    private val allMeals: MutableSet<MealsItems> = mutableSetOf()

    private var currentMeal: MealsEntity? = null

    private val currentMealsFlow = MutableSharedFlow<List<MealsItems>>(replay = 0, extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    private val currentMealFlow = MutableSharedFlow<MealsEntity?>(replay = 0, extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    private suspend fun loadMealsFromServer(category: CategoryItems, filter: (List<MealsItems>) -> List<MealsItems> = {it}) : List<MealsItems>{
        recipeApiService.getMealList(category.strcategory!!).awaitResponse().body().let {

            if (it == null || it.mealsItem.isNullOrEmpty()) {
                return loadMealsFromDataBase(category,filter)
            }

            it.mealsItem!!.forEach{it.categoryName = category.strcategory}

            currentMealsFlow.tryEmit(filter(it.mealsItem!!))
            categoryMeals.put(category.strcategory,it.mealsItem!!)
            allMeals.addAll(it.mealsItem!!)
            insertMealsIntoDataBase(category,it.mealsItem!!)

            return filter(categoryMeals[category.strcategory]!!)
        }
    }
    private suspend fun loadMealsFromDataBase(category: CategoryItems,filter: (List<MealsItems>) -> List<MealsItems> = {it}) : List<MealsItems> {
        dao.getSpecificMealList(category.strcategory!!).let {
            currentMealsFlow.tryEmit(filter(it))
            categoryMeals.put(category.strcategory,it)
            allMeals.addAll(it)

            return filter(categoryMeals[category.strcategory]!!)
        }
    }

    private suspend fun loadMealsFromDataBase(filter: (List<MealsItems>) -> List<MealsItems> = {it}) {
        dao.getSpecificMealList().let {
            currentMealsFlow.tryEmit(filter(it))
            allMeals.addAll(it)
        }
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

    override suspend fun setCurrentMeals(category: CategoryItems?) {
        if (category == null){
            setCurrentMeals()
            return
        }

        if (categoryMeals.get(category.strcategory).isNullOrEmpty()) {
            loadMealsFromServer(category)
            return
        }

        categoryMeals[category.strcategory].let { currentMealsFlow.emit(it!!)}
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
            loadMealsFromServer(category) {it.filter{it.strMeal!!.isContainsLowerCase(filterString)}}
            return
        }

        currentMealsFlow.emit(categoryMeals[category.strcategory]!!.filter{it.strMeal!!.isContainsLowerCase(filterString)})}

    override suspend fun setCurrentMeals(categories: List<CategoryItems>) = flow{
        var progress = 0.0

        for (category in categories) {

            if(categoryMeals[category.strcategory] == null)
                loadMealsFromServer(category)

            progress += ((1.0 / categories.size) * 100).toInt()

            emit(progress.toInt())
        }

        emit(MAX_PROGRESS)

    }.flowOn(dispatcher)


    override suspend fun setCurrentMeals() {
        if(allMeals.toList().isEmpty()) {
            loadMealsFromDataBase()
            return
        }

        currentMealsFlow.emit(allMeals.toList())
    }

    override suspend fun setCurrentMeals(filterString: String?){
        if (filterString.isNullOrBlank()) {
            setCurrentMeals()
            return
        }

        if(allMeals.toList().isEmpty()) {
            loadMealsFromDataBase({it.filter{ it.strMeal!!.isContainsLowerCase(filterString)}})
            return
        }

        currentMealsFlow.emit(allMeals.filter{it.strMeal!!.isContainsLowerCase(filterString)})
    }

    override suspend fun setAvailableCurrentMeals(availableMeals: List<MealsEntity>){
        if(allMeals.toList().isEmpty())
            loadMealsFromDataBase{it.filter {meal -> availableMeals.find{it.strmeal == meal.strMeal} != null}}

        currentMealsFlow.emit(allMeals.filter { meal -> availableMeals.find{it.strmeal == meal.strMeal} != null})
    }

    override fun getCurrentMeal(): MealsEntity? = currentMeal

    override fun setCurrentMeal(meal: MealsEntity?) {
        currentMeal = meal
        currentMealFlow.tryEmit(meal)
    }

    override suspend fun setCurrentMeal(id: Int) {
        recipeApiService.getSpecificItem(id.toString()).awaitResponse().body()!!.let {
            setCurrentMeal(it.mealsEntity.first())
        }
    }

    override fun listenCurrentMeals(): Flow<List<MealsItems>> = currentMealsFlow

    override fun listenCurrentMeal(): Flow<MealsEntity?> = currentMealFlow

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