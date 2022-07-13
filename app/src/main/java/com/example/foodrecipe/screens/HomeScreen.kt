package com.example.foodrecipe.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodrecipe.R
import com.example.foodrecipe.adapter.MainCategoryAdapter
import com.example.foodrecipe.adapter.SubCategoryAdapter
import com.example.foodrecipe.database.RecipeDatabase
import com.example.foodrecipe.databinding.FragmentHomeBinding
import com.example.foodrecipe.model.ApiStatus
import com.example.foodrecipe.viewmodel.CategoryDaoViewModel
import com.example.foodrecipe.viewmodel.MealListDaoViewModel
import com.example.foodrecipe.viewmodel.MealDaoViewModel
import com.example.foodrecipe.viewmodel.factory.CategoryViewModelFactory
import com.example.foodrecipe.viewmodel.factory.MealListViewModelFactory
import com.example.foodrecipe.viewmodel.factory.MealViewModelFactory

class HomeScreen : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private lateinit var mainCategoryAdapter: MainCategoryAdapter
    private lateinit var subCategoryAdapter: SubCategoryAdapter

    private val binding get() = _binding!!

    private val categoryViewModel: CategoryDaoViewModel by activityViewModels {
        CategoryViewModelFactory(RecipeDatabase.getDatabase(requireContext()).recipeDao())
    }

    private val mealListViewModel: MealListDaoViewModel by activityViewModels {
        MealListViewModelFactory(RecipeDatabase.getDatabase(requireContext()).recipeDao())
    }

    private val mealViewModel: MealDaoViewModel by activityViewModels {
        MealViewModelFactory(RecipeDatabase.getDatabase(requireContext()).recipeDao())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        mainCategoryAdapter = MainCategoryAdapter(requireContext(),categoryViewModel.category.value?.categorieitems!!,
            object :MainCategoryAdapter.OnItemClickListener {
                override fun onClicked(id: Int) {
                    mealListViewModel.changeCategory(categoryViewModel.category.value?.categorieitems!![id])
                }})

        subCategoryAdapter = SubCategoryAdapter(requireContext(), mealListViewModel.meal.value?.mealsItem!!,object:SubCategoryAdapter.OnItemClickListener{

            override fun onClicked(id: Int) {
                mealViewModel.loadMealFromNetwork(id)
            }
        })

        binding.mainCategoryRecyclerView.adapter = mainCategoryAdapter
        binding.mainCategoryRecyclerView.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)

        binding.subCategoryRecyclerView.adapter = subCategoryAdapter
        binding.subCategoryRecyclerView.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)

        mealListViewModel.category.observe(this.viewLifecycleOwner) {
            binding.categoryNameTextView.text = it
            if (it.isNotBlank()) mealListViewModel.loadMealFromNetwork(it)
        }

        mealListViewModel.meal.observe(this.viewLifecycleOwner) {
            if (it?.mealsItem != null){
                subCategoryAdapter.data = it.mealsItem!!
                subCategoryAdapter.subdata = it.mealsItem!!
            }
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            var letter:String? = null

            override fun onQueryTextSubmit(query: String?): Boolean {
                subCategoryAdapter.filterString = query
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                if (newText== null) return true

                mealListViewModel.changeCategory("")

                if(letter == null){

                    if (newText.isNotBlank()) {
                        letter = newText
                        mealListViewModel.loadMealFromNetwork(letter!!.first())
                    }
                }

                if (newText.isNotBlank() && (newText.get(0) != (letter?.get(0) ?: ""))) {
                    letter = newText
                    if (letter!!.first().isLetter())
                        mealListViewModel.loadMealFromNetwork(letter!!.first())

                }

                subCategoryAdapter.filterString = newText

                return  true
            }
        })

        mealViewModel.status.observe(viewLifecycleOwner) {

            when(it){
                ApiStatus.DONE -> goToDetailScreen()
                ApiStatus.ERROR -> Toast.makeText(requireContext(),getString(R.string.error_detail_screen_loading),Toast.LENGTH_LONG).show()
            }
        }

        binding.categoryNameTextView.text = mealListViewModel.category.value

        return binding.root
    }

    private fun goToDetailScreen() {
        findNavController().navigate(R.id.action_homeScreen_to_detailScreen)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}