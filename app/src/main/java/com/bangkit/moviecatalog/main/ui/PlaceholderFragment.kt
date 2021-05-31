package com.bangkit.moviecatalog.main.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.moviecatalog.R
import com.bangkit.moviecatalog.core.domain.model.MovieModel
import com.bangkit.moviecatalog.core.ui.ClickCallback
import com.bangkit.moviecatalog.core.ui.ListAdapter
import com.bangkit.moviecatalog.databinding.FragmentMainBinding
import com.bangkit.moviecatalog.detail.DetailActivity
import com.bangkit.moviecatalog.main.list.ListFooterAdapter
import com.bangkit.moviecatalog.main.viewmodel.ListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * A placeholder fragment containing a simple view.
 */
@AndroidEntryPoint
class PlaceholderFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
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
    private val listViewModel by viewModels<ListViewModel>()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            val decoration =
                DividerItemDecoration(binding.rvListItem.context, DividerItemDecoration.VERTICAL)
            binding.rvListItem.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(requireContext())
                addItemDecoration(decoration)
                adapter = listAdapter.withLoadStateFooter(ListFooterAdapter { listAdapter.retry() } )

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
                listViewModel.fetchMovie(
                    arguments?.getString(ARG_SECTION_TYPE)
                        ?: resources.getString(R.string.type_movie)
                ).collectLatest {
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
        fun newInstance(sectionNumber: Int, sectionType: String): PlaceholderFragment {
            return PlaceholderFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                    putString(ARG_SECTION_TYPE, sectionType)
                }
            }
        }
    }
}