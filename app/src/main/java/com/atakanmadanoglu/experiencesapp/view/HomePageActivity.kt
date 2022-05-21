package com.atakanmadanoglu.experiencesapp.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.atakanmadanoglu.experiencesapp.databinding.ActivityHomePageBinding

class HomePageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomePageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomePageBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setToolbar()
        setupNavigationView()
    }

   private fun setToolbar() {
       val navHostFragment = binding.fragmentContainerView.getFragment<NavHostFragment>()
       val navController = navHostFragment.navController
       val builder = AppBarConfiguration.Builder(navController.graph)
       val drawerLayout = binding.drawerLayout
       builder.setOpenableLayout(drawerLayout)
       val appBarConfiguration = builder.build()
       binding.toolbar.setupWithNavController(navController, appBarConfiguration)
   }
   private fun setupNavigationView() {
       val navHostFragment = binding.fragmentContainerView.getFragment<NavHostFragment>()
       val navController = navHostFragment.navController
       NavigationUI.setupWithNavController(binding.navigationView, navController)
   }
}