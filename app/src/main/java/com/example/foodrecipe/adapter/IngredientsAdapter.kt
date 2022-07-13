package com.example.foodrecipe.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foodrecipe.databinding.IngredienceItemBinding
import com.example.foodrecipe.model.Ingredient

class IngredientsAdapter(val ingredients: List<Ingredient>, colors: List<Pair<Color, Color>>):BaseColorAdapter<IngredientsAdapter.IngredientViewHolder>(colors) {

    class IngredientViewHolder(val binding:IngredienceItemBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        return IngredientViewHolder(IngredienceItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }


    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {

        val ingredient = ingredients.get(position)
        val colors = colors[position % colors.size]

        holder.binding.apply {


            idIngredienceTextView.background.setTint(colors.first.toArgb())
            idIngredienceTextView.text = ingredient.id.toString()


            nameMealTextView.text = ingredient.name

            measureTextView.background.setTint(colors.second.toArgb())
            measureTextView.text = ingredient.measure

        }
    }

    override fun getItemCount(): Int {
        return ingredients.size
    }

}