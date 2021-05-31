package com.bangkit.moviecatalog.favorites.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.moviecatalog.R
import com.bangkit.moviecatalog.core.domain.model.MovieModel
import com.bangkit.moviecatalog.core.ui.ClickCallback
import com.bangkit.moviecatalog.core.ui.ListAdapter
import com.bangkit.moviecatalog.detail.DetailActivity
import com.bangkit.moviecatalog.di.FavoriteModuleDependencies
import com.bangkit.moviecatalog.favorites.DaggerFavoriteComponent
import com.bangkit.moviecatalog.favorites.databinding.FragmentFavoriteBinding
import com.bangkit.moviecatalog.favorites.ui.viewmodel.ListFavViewModel
import com.bangkit.moviecatalog.favorites.viewmodel.ViewModelFactory
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * A placeholder fragment containing a simple view.
 */
class FavoritesFragment : Fragment() {

    @Inject
    lateinit var factory: ViewModelFactory

    private val listViewModel by activityViewModels<ListFavViewModel>{ factory }

    private lateinit var binding: FragmentFavoriteBinding
    private val listAdapter = ListAdapter(object : ClickCallback<MovieModel> {
        override fun onClick(data: MovieModel) {
            val detailIntent = Intent(requireContext(), DetailActivity::class.java)
            detailIntent.apply {
                putExtra(DetailActivity.EXTRA_ID, data.id)
                putExtra(DetailActivity.EXTRA_TYPE, data.type)
            }
            requireContext().startActivity(detailIntent)
        }
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerFavoriteComponent.builder()
            .context(requireContext())
            .appDependencies(
                EntryPointAccessors.fromApplication(
                    requireContext(),
                    FavoriteModuleDependencies::class.java
                )
            )
            .build()
            .inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoriteBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity!=null) {
            val decoration = DividerItemDecoration(binding.rvListItem.context, DividerItemDecoration.VERTICAL)
            binding.rvListItem.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(requireContext())
                addItemDecoration(decoration)
                adapter = listAdapter
            }
            listAdapter.addLoadStateListener { loadState ->
                // Only show the list if refresh succeeds.
                when (loadState.mediator?.refresh) {
                    is LoadState.NotLoading -> {
                        binding.rvListItem.visibility = View.VISIBLE
                        binding.progressBar.visibility = View.GONE
                    }
                    is LoadState.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                        binding.rvListItem.visibility = View.GONE
                    }
                }
            }

            viewLifecycleOwner.lifecycleScope.launch {
                listViewModel.fetchMovie(arguments?.getString(ARG_SECTION_TYPE) ?: resources.getString(R.string.type_movie))
                    .collectLatest {
                        listAdapter.submitData(it)
                    }
            }
        }
    }

    companion object {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private const val ARG_SECTION_TYPE = "section_type"
        private const val ARG_SECTION_NUMBER = "section_number"

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        @JvmStatic
        fun newInstance(sectionNumber: Int, sectionType: String): FavoritesFragment {
            return FavoritesFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                    putString(ARG_SECTION_TYPE, sectionType)
                }
            }
        }
    }
}