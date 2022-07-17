package com.example.foodrecipe.view.adapter

import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView

abstract class BaseColorAdapter<T : RecyclerView.ViewHolder?>(val colors: List<Pair<Color,Color>>): RecyclerView.Adapter<T>()