package com.example.foodrecipe.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodrecipe.databinding.ItemSubCategoryBinding
import com.example.foodrecipe.model.entities.MealsItems

class SubCategoryAdapter(
    private val context: Context,
    val listener: OnItemClickListener
): RecyclerView.Adapter<SubCategoryAdapter.RecipeViewHolder>() {

    var data:List<MealsItems> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class RecipeViewHolder(val binding: ItemSubCategoryBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        return RecipeViewHolder(ItemSubCategoryBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val item = data[position]

        holder.binding.nameDish.text = item.strMeal
        Glide.with(context).load(item.strMealThumb).into(holder.binding.imageDish)
        holder.binding.root.setOnClickListener {listener.onClicked(item)}
    }

    interface OnItemClickListener{
        fun onClicked(meal:MealsItems)
    }

    override fun getItemCount(): Int {
        return data.size
    }
}