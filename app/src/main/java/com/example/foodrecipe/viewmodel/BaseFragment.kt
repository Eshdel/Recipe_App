package com.example.foodrecipe.viewmodel

import androidx.fragment.app.Fragment
import com.example.foodrecipe.view.Navigator

abstract class BaseFragment:Fragment() {
    val navigator by lazy {activity as Navigator }
}