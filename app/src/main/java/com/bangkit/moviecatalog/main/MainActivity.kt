package com.bangkit.moviecatalog.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.bangkit.moviecatalog.R
import com.bangkit.moviecatalog.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navBar = binding.navigationBar
        val navController = findNavController(R.id.nav_host_fragment)

        navBar.setupWithNavController(navController)

        navBar.setOnNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.favorite_page -> {
            val uri = Uri.parse("moviecatalog://favorites")
            startActivity(Intent(Intent.ACTION_VIEW, uri))
            false
        }
        R.id.home_page -> true
        else -> false
    }
}