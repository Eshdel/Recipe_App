package com.example.foodrecipe.view.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.foodrecipe.databinding.FragmentSplashBinding
import com.example.foodrecipe.model.repostitory.meals.MAX_PROGRESS
import com.example.foodrecipe.view.Navigator
import com.example.foodrecipe.viewmodel.BaseFragment
import com.example.foodrecipe.viewmodel.SplashScreenViewModel
import com.example.foodrecipe.viewmodel.factory.*

class SplashScreen: BaseFragment(){

    private val viewModel:SplashScreenViewModel by baseViewModels()

    private var _binding: FragmentSplashBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)

        viewModel.loadingProgress.observe(viewLifecycleOwner) {
            binding.progressBar.progress = it
            if (it == MAX_PROGRESS)
                navigator.goHomeScreen()
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}