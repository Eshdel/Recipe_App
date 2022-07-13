package com.example.foodrecipe.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodrecipe.databinding.ItemSubCategoryBinding
import com.example.foodrecipe.model.entities.MealsItems
import com.example.foodrecipe.model.entities.Recipes

class SubCategoryAdapter(
    private val context: Context,
    data: List<MealsItems>,
    val listener: OnItemClickListener
): RecyclerView.Adapter<SubCategoryAdapter.RecipeViewHolder>() {

    var subdata:List<MealsItems> = data

    var filterString:String? = null
        set(value) {
            field = value
            data = Filter(subdata)
        }

    var data:List<MealsItems> = data
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
        holder.binding.root.setOnClickListener {listener.onClicked(item.idMeal.toInt())}
    }

    interface OnItemClickListener{
        fun onClicked(id:Int)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    private fun Filter(meals:List<MealsItems>):List<MealsItems> {

        if(filterString.isNullOrBlank()) return meals

        return meals.filter { it.strMeal.lowercase().contains(filterString!!.lowercase())}
    }

    companion object

}