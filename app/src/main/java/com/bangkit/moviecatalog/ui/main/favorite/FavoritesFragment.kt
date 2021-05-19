package com.bangkit.moviecatalog.ui.main.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.moviecatalog.R
import com.bangkit.moviecatalog.databinding.FragmentMainBinding
import com.bangkit.moviecatalog.ui.main.list.ListAdapter
import com.bangkit.moviecatalog.ui.main.viewmodel.ListFavViewModel
import com.bangkit.moviecatalog.viewmodel.ViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * A placeholder fragment containing a simple view.
 */
class FavoritesFragment : Fragment() {

    private lateinit var listViewModel: ListFavViewModel
    private lateinit var binding: FragmentMainBinding
    private val listAdapter = ListAdapter()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listViewModel = ViewModelProvider(requireActivity(),
            ViewModelFactory.getInstance(requireActivity()))[ListFavViewModel::class.java]

    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(layoutInflater, container, false)
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