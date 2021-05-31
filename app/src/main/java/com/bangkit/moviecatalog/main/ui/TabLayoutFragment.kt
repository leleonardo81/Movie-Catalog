package com.bangkit.moviecatalog.main.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.bangkit.moviecatalog.R
import com.bangkit.moviecatalog.databinding.FragmentTabLayoutBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

/**
 * A simple [Fragment] subclass.
 * Use the [TabLayoutFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TabLayoutFragment : Fragment() {
    private lateinit var binding: FragmentTabLayoutBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTabLayoutBinding.inflate(inflater, container, false)
        arguments?.apply {
            val isFavoritePage = TabLayoutFragmentArgs.fromBundle(this).isFavoritePage
            if (isFavoritePage) binding.appName.text = resources.getString(R.string.favorite)
            val sectionsPagerAdapter =
                SectionsPagerAdapter(requireParentFragment(), isFavoritePage)
            val viewPager: ViewPager2 = binding.viewPager
            viewPager.adapter = sectionsPagerAdapter
            val tabs: TabLayout = binding.tabs
            TabLayoutMediator(tabs, viewPager) { tab, position ->
                tab.id = SectionsPagerAdapter.TAB_ID[position]
                tab.text = resources.getString(SectionsPagerAdapter.TAB_TITLES[position])
            }.attach()
        }

        return binding.root
    }

    companion object {
        private const val ARG_FAV_PAGE = "fav_page"
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param isFavoritePage Page for Favprite.
         * @return A new instance of fragment TabLayoutFragment.
         */
        @JvmStatic
        fun newInstance(isFavoritePage: Boolean = false) =
            TabLayoutFragment().apply {
                arguments = Bundle().apply {
                    putBoolean(ARG_FAV_PAGE, isFavoritePage)
                }
            }
    }
}
