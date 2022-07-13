package com.example.foodrecipe.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodrecipe.databinding.ItemMainCategoryBinding
import com.example.foodrecipe.model.entities.CategoryItems

class MainCategoryAdapter(private val context: Context, val data:List<CategoryItems>,val listener: OnItemClickListener):RecyclerView.Adapter<MainCategoryAdapter.RecipeViewHolder>() {

    class RecipeViewHolder(val binding: ItemMainCategoryBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        return RecipeViewHolder(ItemMainCategoryBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val item = data[position]

        holder.binding.nameDish.text  = item.strcategory
        Glide.with(context).load(item.strcategorythumb).into(holder.binding.imageDish)
        holder.binding.root.setOnClickListener{listener.onClicked(position)}
    }

    override fun getItemCount(): Int {
        return data.size
    }

    interface OnItemClickListener{
        fun onClicked(id:Int)
    }
}