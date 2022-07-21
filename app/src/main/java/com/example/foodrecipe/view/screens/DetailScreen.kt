package com.example.foodrecipe.view.screens

import android.content.Context
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.toColor
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.foodrecipe.App
import com.example.foodrecipe.R
import com.example.foodrecipe.view.adapter.IngredientsAdapter
import com.example.foodrecipe.view.adapter.InstructionAdapter
import com.example.foodrecipe.databinding.FragmentDetailScreenBinding
import com.example.foodrecipe.model.entities.MealsEntity
import com.example.foodrecipe.viewmodel.BaseFragment
import com.example.foodrecipe.viewmodel.DetailScreenViewModel
import com.example.foodrecipe.viewmodel.factory.DetailScreenViewModelFactory


class DetailScreen: BaseFragment() {

    private var _binding: FragmentDetailScreenBinding? = null

    private val binding get() = _binding!!

    private lateinit var mealsEntity:MealsEntity

    private lateinit var ingredientsAdapter: IngredientsAdapter

    private lateinit var instructionAdapter: InstructionAdapter

    val viewModel:DetailScreenViewModel by viewModels {
        DetailScreenViewModelFactory(arguments?.getParcelable("meal")!!,requireContext().applicationContext as App)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mealsEntity = arguments?.getParcelable("meal")!!

        if (mealsEntity == null){
            navigator.goBack()
            return
        }

        ingredientsAdapter = IngredientsAdapter(viewModel.ingredience.value!!, getColorsPreset(requireContext()))

        instructionAdapter = InstructionAdapter(viewModel.instructions.value!!, getColorsPreset(requireContext()))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        _binding = FragmentDetailScreenBinding.inflate(inflater,container,false)

        if (mealsEntity != null){
            binding.ingredientsRecyclerView.adapter = ingredientsAdapter
            binding.ingredientsRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)

            binding.instructionsRecyclerView.adapter = instructionAdapter
            binding.instructionsRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)

            binding.categoryNameTextView.text = viewModel.meal?.strmeal

            binding.goBack.setOnClickListener {navigator.goBack() }

            Glide.with(requireContext()).load(viewModel.meal?.strmealthumb).into(binding.imageItem)

            binding.searchYoutube.setOnClickListener {
                navigator.openYoutubeVideo(Uri.parse(viewModel.meal?.stryoutube))
            }
        }

        return binding.root
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


