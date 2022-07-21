package com.example.foodrecipe.view.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodrecipe.view.adapter.MainCategoryAdapter
import com.example.foodrecipe.view.adapter.SubCategoryAdapter
import com.example.foodrecipe.databinding.FragmentHomeBinding
import com.example.foodrecipe.model.entities.CategoryItems
import com.example.foodrecipe.model.entities.MealsItems
import com.example.foodrecipe.viewmodel.BaseFragment
import com.example.foodrecipe.viewmodel.HomeScreenViewModel


import com.example.foodrecipe.viewmodel.factory.baseViewModels

class HomeScreen: BaseFragment() {

    private var _binding: FragmentHomeBinding? = null

    private lateinit var mainCategoryAdapter: MainCategoryAdapter

    private lateinit var subCategoryAdapter: SubCategoryAdapter

    private val binding get() = _binding!!

    private val viewModel:HomeScreenViewModel by baseViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        mainCategoryAdapter = MainCategoryAdapter(requireContext(),
            object :MainCategoryAdapter.OnItemClickListener {
                override fun onClicked(category: CategoryItems) {viewModel.selectCategory(category)}})

        subCategoryAdapter = SubCategoryAdapter(requireContext(), object:SubCategoryAdapter.OnItemClickListener {
            override fun onClicked(meal: MealsItems) {
                viewModel.currentMeal.observe(viewLifecycleOwner){
                    navigator.goLoadingScreen(it)
                }

                viewModel.setCurrentMeal(meal)
            }})

        binding.mainCategoryRecyclerView.adapter = mainCategoryAdapter
        binding.mainCategoryRecyclerView.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)

        binding.subCategoryRecyclerView.adapter = subCategoryAdapter
        binding.subCategoryRecyclerView.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.setSearchString(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.setSearchString(newText)
                return  true
            }
        })

        viewModel.currentCategory.observe(viewLifecycleOwner){
            binding.categoryNameTextView.text = it?.strcategory ?: "All"

            if (binding.categoryNameTextView.text == "All")
                binding.unselectCategory.visibility = View.INVISIBLE
            else
                binding.unselectCategory.visibility = View.VISIBLE
        }

        viewModel.currentCategories.observe(viewLifecycleOwner){
            mainCategoryAdapter.data = it ?: listOf()
        }

        viewModel.currentMealsList.observe(viewLifecycleOwner){
            subCategoryAdapter.data = it ?: listOf()
            if (it.isNullOrEmpty()) {
                binding.noDataImage.visibility = View.VISIBLE
                binding.tryAgainLoadDataButton.visibility = View.VISIBLE
            }
            else{
                binding.noDataImage.visibility = View.INVISIBLE
                binding.tryAgainLoadDataButton.visibility = View.INVISIBLE
            }
        }

        binding.unselectCategory.setOnClickListener {
            viewModel.unselectCategory()
        }

        binding.tryAgainLoadDataButton.setOnClickListener {
            viewModel.syncData(viewModel.currentCategory.value)
        }

        binding.syncDataButton.setOnClickListener {
            viewModel.syncData()
            viewModel.unselectCategory()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.showAllMeals()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}