package com.bangkit.moviecatalog.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.bangkit.moviecatalog.R
import com.bangkit.moviecatalog.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navBar = binding.navigationBar
        val navController = findNavController(R.id.nav_host_fragment)

        navBar.setupWithNavController(navController)
    }
}