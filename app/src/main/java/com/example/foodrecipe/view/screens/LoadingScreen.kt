package com.example.foodrecipe.view.screens
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.foodrecipe.databinding.LoadingScreenBinding
import com.example.foodrecipe.viewmodel.BaseFragment
import com.example.foodrecipe.viewmodel.LoadingScreenViewModel

import com.example.foodrecipe.viewmodel.factory.baseViewModels

class LoadingScreen: BaseFragment() {

    private var _binding: LoadingScreenBinding? = null

    private val binding get() = _binding!!

    private val viewModel: LoadingScreenViewModel by baseViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = LoadingScreenBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.isErrorLoading.observe(viewLifecycleOwner) {
            if (it == true) {
                navigator.goBack()
                Toast.makeText(requireContext(),"Error loading !",Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.meal.observe(viewLifecycleOwner) {
            navigator.goDetailScreen(it)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}