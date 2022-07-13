package com.example.foodrecipe.adapter

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.foodrecipe.dao.RecipeDao
import com.example.foodrecipe.databinding.InstructionItemBinding
import com.example.foodrecipe.model.Instruction

class InstructionAdapter(val instructions: MutableList<Instruction>, colors: List<Pair<Color, Color>> ) : BaseColorAdapter<InstructionAdapter.InstructionViewHolder>(colors){

    class InstructionViewHolder(val binding: InstructionItemBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InstructionViewHolder {
        return InstructionViewHolder(InstructionItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: InstructionViewHolder, position: Int) {

        val instruction = instructions.get(position)

        val colors = colors[position % colors.size]

        holder.binding.apply {

            idInstructionTextView.text = instruction.id.toString()

            descriptionTextView.text = instruction.description

            constraintLayout.background.setTint(colors.first.toArgb())

            idInstructionTextView.background.setTint(colors.second.toArgb())
        }
    }

    override fun getItemCount(): Int {
        return instructions.size
    }
}