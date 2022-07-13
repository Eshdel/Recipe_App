package com.example.foodrecipe.screens

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.toColor
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.foodrecipe.R
import com.example.foodrecipe.adapter.IngredientsAdapter
import com.example.foodrecipe.adapter.InstructionAdapter
import com.example.foodrecipe.database.RecipeDatabase
import com.example.foodrecipe.databinding.FragmentDetailScreenBinding
import com.example.foodrecipe.viewmodel.DetailScreenViewModel
import com.example.foodrecipe.viewmodel.MealDaoViewModel
import com.example.foodrecipe.viewmodel.factory.DetailScreenViewModelFactory
import com.example.foodrecipe.viewmodel.factory.MealViewModelFactory


class DetailScreen : Fragment() {

    private var _binding: FragmentDetailScreenBinding? = null

    private val binding get() = _binding!!

    private val mealViewModel:MealDaoViewModel by activityViewModels {
        MealViewModelFactory(RecipeDatabase.getDatabase(requireContext()).recipeDao())
    }

    private val detailScreenViewModel:DetailScreenViewModel by viewModels {
        DetailScreenViewModelFactory(mealViewModel.mealResponse.value!!)
    }

    lateinit var ingredientsAdapter: IngredientsAdapter

    lateinit var instructionAdapter: InstructionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        ingredientsAdapter = IngredientsAdapter(detailScreenViewModel.ingredience.value!!, getColorsPreset(requireContext()))

        instructionAdapter = InstructionAdapter(detailScreenViewModel.instructions.value!!,

            getColorsPreset(requireContext()))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        _binding = FragmentDetailScreenBinding.inflate(inflater,container,false)

        binding.ingredientsRecyclerView.adapter = ingredientsAdapter
        binding.ingredientsRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)

        binding.instructionsRecyclerView.adapter = instructionAdapter
        binding.instructionsRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)

        binding.categoryNameTextView.text = mealViewModel.meal?.strmeal

        binding.goBack.setOnClickListener { findNavController().popBackStack() }

        Glide.with(requireContext()).load(mealViewModel.meal?.strmealthumb).into(binding.imageItem)

        binding.searchYoutube.setOnClickListener {
            openVideo()
        }

        return binding.root
    }

    private fun openVideo() {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(mealViewModel.meal?.stryoutube))
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.setPackage("com.google.android.youtube")
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        private lateinit var context:Context

        fun getColorsPreset(context: Context):List<Pair<Color,Color>>{

            this.context= context

            return listOf(
                getColorPair(R.color.blue,R.color.yellow),
                getColorPair(R.color.turquoise,R.color.orange),
                getColorPair(R.color.yellow,R.color.violet),
                getColorPair(R.color.pink,R.color.green))
        }

        private fun getColorPair(idFirst:Int,idSecond:Int): Pair<Color,Color> {
            return Pair(context.getColor(idFirst).toColor(), context.getColor(idSecond).toColor())
        }
    }
}


