package com.bangkit.moviecatalog.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bangkit.moviecatalog.R
import com.bangkit.moviecatalog.ui.main.all.PlaceholderFragment
import com.bangkit.moviecatalog.ui.main.favorite.FavoritesFragment


/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(private val fragment: Fragment, private val isFavoritePage: Boolean)
    : FragmentStateAdapter(fragment) {

    companion object {
        val TAB_TITLES = arrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )

        val TAB_ID = arrayOf(
            R.string.tab_movie_id,
            R.string.tab_tv_id
        )

        private val TYPES = arrayOf(
            R.string.type_movie,
            R.string.type_tv
        )
    }

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment = when (isFavoritePage) {
        true -> FavoritesFragment.newInstance(position+1,
            fragment.resources.getString(TYPES[position]))
        false -> PlaceholderFragment.newInstance(position+1,
            fragment.resources.getString(TYPES[position]))

    }
}