package com.example.foodrecipe.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "Recipes")
data class Recipes(
    @PrimaryKey(autoGenerate = true)
    val id:Int,

    @ColumnInfo(name = "dish_name")
    val dishName:String
):Serializable
