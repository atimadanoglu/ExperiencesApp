package com.atakanmadanoglu.experiencesapp.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.atakanmadanoglu.experiencesapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val sharedPref = getSharedPreferences("userInformation", Context.MODE_PRIVATE)
        sharedPref.edit().apply {
            putBoolean("isSignedIn", false)
            apply()
        }
        val isSignedIn = sharedPref.getBoolean("isSignedIn", false)
        if (isSignedIn) {
            val intent = Intent(this, HomePageActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}