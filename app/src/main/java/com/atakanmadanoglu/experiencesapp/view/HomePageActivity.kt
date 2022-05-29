package com.atakanmadanoglu.experiencesapp.view

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.atakanmadanoglu.experiencesapp.databinding.ActivityHomePageBinding
import com.atakanmadanoglu.experiencesapp.databinding.DrawerHeaderBinding

class HomePageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomePageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomePageBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setToolbar()
        setupNavigationView()
        setDrawerHeader()
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

    private fun setDrawerHeader() {
        val sharedPref = this.getSharedPreferences("userInformation", Context.MODE_PRIVATE)
        val email = sharedPref.getString("email", "")
        val header = binding.navigationView.getHeaderView(0)
        val binding = DrawerHeaderBinding.bind(header)
        binding.email.text = email
    }
}