package com.example.foodrecipe

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.foodrecipe.databinding.ActivityMainBinding
import com.example.foodrecipe.model.entities.MealsEntity
import com.example.foodrecipe.view.Navigator

class MainActivity : AppCompatActivity(), Navigator {

    private lateinit var binding: ActivityMainBinding

    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, true)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHost = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment
        navController = navHost.navController
    }

    override fun goHomeScreen() {
        navController.navigate(R.id.action_splashScreen_to_homeScreen2)
    }

    override fun goDetailScreen(meal: MealsEntity) {
        navController.navigate(R.id.action_loadingScreen_to_detailScreen, bundleOf("meal" to meal))
    }

    override fun goLoadingScreen(meal:MealsEntity?) {
       navController.navigate(R.id.action_homeScreen_to_loadingScreen, bundleOf("meal" to meal))
    }

    override fun goBack() {
        navController.popBackStack()
    }

    override fun openYoutubeVideo(uri: Uri) {
        try {

            val intent = Intent(Intent.ACTION_VIEW, uri)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.setPackage("com.google.android.youtube")
            startActivity(intent)
        }
        catch (e:Exception){
            Toast.makeText(this,"Sorry, Failed to open video",Toast.LENGTH_SHORT).show()
        }
    }

    override fun closeApp() {
        finish()
    }
}