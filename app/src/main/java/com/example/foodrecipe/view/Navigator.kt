package com.example.foodrecipe.view

import android.net.Uri
import com.example.foodrecipe.model.entities.MealsEntity

interface Navigator {
    fun goHomeScreen()

    fun goDetailScreen(meal:MealsEntity)

    fun goLoadingScreen(meal:MealsEntity?)

    fun goBack()

    fun openYoutubeVideo(uri: Uri)

    fun  closeApp()
}