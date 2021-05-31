package com.bangkit.moviecatalog.favorites

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.bangkit.moviecatalog.MainApplication
import com.bangkit.moviecatalog.favorites.databinding.ActivityFavoriteBinding
import com.bangkit.moviecatalog.favorites.ui.SectionsPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sectionsPagerAdapter =
            SectionsPagerAdapter(this)
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.id = SectionsPagerAdapter.TAB_ID[position]
            tab.text = resources.getString(SectionsPagerAdapter.TAB_TITLES[position])
        }.attach()

        binding.backButton.setOnClickListener {
            navigateUpTo(Intent(this, MainApplication::class.java))
        }
    }
}
