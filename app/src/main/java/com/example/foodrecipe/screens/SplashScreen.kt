package com.example.foodrecipe.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.foodrecipe.R
import com.example.foodrecipe.database.RecipeDatabase

import com.example.foodrecipe.databinding.FragmentSplashBinding
import com.example.foodrecipe.model.ApiStatus
import com.example.foodrecipe.viewmodel.CategoryDaoViewModel
import com.example.foodrecipe.viewmodel.MealListDaoViewModel
import com.example.foodrecipe.viewmodel.factory.CategoryViewModelFactory
import com.example.foodrecipe.viewmodel.factory.MealListViewModelFactory
import kotlinx.coroutines.delay

class SplashScreen : Fragment(){

    private val categoryViewModel:CategoryDaoViewModel by activityViewModels {
        CategoryViewModelFactory(RecipeDatabase.getDatabase(requireContext()).recipeDao())
    }

    private val mealViewModel:MealListDaoViewModel by activityViewModels {
        MealListViewModelFactory(RecipeDatabase.getDatabase(requireContext()).recipeDao())
    }

    private var _binding: FragmentSplashBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        categoryViewModel.loadCategoryFromNetwork()

        categoryViewModel.status.observe(this.viewLifecycleOwner) {

            when(it){

                ApiStatus.DONE-> {
                    if (categoryViewModel.category.value?.categorieitems!!.isEmpty()) {

                        Toast.makeText(requireContext(),getText(R.string.error_detail_screen_loading),Toast.LENGTH_LONG).show()

                        categoryViewModel.loadCategoryFromNetwork()

                        return@observe
                    }

                    categoryViewModel.category.value?.categorieitems?.first()?.let { it1 ->
                        mealViewModel.changeCategory(it1)
                        mealViewModel.loadMealFromNetwork(it1.strcategory)
                    }
                }
            }
        }

        mealViewModel.status.observe(this.viewLifecycleOwner) {
            when(it) {
                ApiStatus.DONE -> goToHomeScreen()
            }
        }

        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun goToHomeScreen() {
        findNavController().navigate(R.id.action_splashScreen_to_homeScreen)
    }
}