package com.example.foodrecipe.viewmodel

import android.os.Debug
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodrecipe.model.Ingredient
import com.example.foodrecipe.model.Instruction
import com.example.foodrecipe.model.entities.MealsEntity
import com.example.foodrecipe.model.repostitory.meals.MealsRepository
import kotlinx.coroutines.delay

class DetailScreenViewModel(val meal: MealsEntity,private val mealsRepository: MealsRepository): ViewModel() {

    private val _ingredients = MutableLiveData<List<Ingredient>>()

    val ingredience get() = _ingredients

    private val _instructions = MutableLiveData<MutableList<Instruction>>(mutableListOf())

    val instructions get() = _instructions

    init {
        _ingredients.value = meal.let {
            listOf (
                Ingredient(1,it.stringredient1,it.strmeasure1),
                Ingredient(2,it.stringredient2,it.strmeasure2),
                Ingredient(3,it.stringredient3,it.strmeasure3),
                Ingredient(4,it.stringredient4,it.strmeasure4),
                Ingredient(5,it.stringredient5,it.strmeasure5),
                Ingredient(6,it.stringredient6,it.strmeasure6),
                Ingredient(7,it.stringredient7,it.strmeasure7),
                Ingredient(8,it.stringredient8,it.strmeasure8),
                Ingredient(9,it.stringredient9,it.strmeasure9),
                Ingredient(10,it.stringredient10,it.strmeasure10),
                Ingredient(11,it.stringredient11,it.strmeasure11),
                Ingredient(12,it.stringredient12,it.strmeasure12),
                Ingredient(13,it.stringredient13,it.strmeasure13),
                Ingredient(14,it.stringredient14,it.strmeasure14),
                Ingredient(15,it.stringredient15,it.strmeasure15),
                Ingredient(16,it.stringredient16,it.strmeasure16),
                Ingredient(17,it.stringredient17,it.strmeasure17),
                Ingredient(18,it.stringredient18,it.strmeasure18),
                Ingredient(19,it.stringredient19,it.strmeasure19),
                Ingredient(20,it.stringredient20,it.strmeasure20)
            )}.filter{it.name?.isNotBlank() ?: false}

        _instructions.value = meal.strinstructions?.split("\n").let{
            var id = 0

            it?.forEach(){
                if (it.trim().isNotBlank())
                    _instructions.value!!.add(Instruction(++id,it))
            }

            _instructions.value }
    }

    override fun onCleared() {
        mealsRepository.setCurrentMeal(null)
        super.onCleared()
    }
}