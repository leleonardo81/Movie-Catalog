package com.bangkit.moviecatalog.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bangkit.moviecatalog.R
import com.bangkit.moviecatalog.databinding.DetailFragmentBinding
import com.bangkit.moviecatalog.detail.viewmodel.DetailViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {

    companion object {
        const val ARG_TYPE = "detail_type"
        const val ARG_ID = "detail_id"
        fun newInstance(detailId: Int, detailType: String) = DetailFragment().apply {
            arguments = Bundle().apply {
                putInt(ARG_ID, detailId)
                putString(ARG_TYPE, detailType)
            }
        }
    }

    private lateinit var binding: DetailFragmentBinding
    private val viewModel by activityViewModels<DetailViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loading.observe(viewLifecycleOwner, {
            binding.progressBar.visibility = when (it) {
                true -> View.VISIBLE
                false -> View.GONE
            }
        })

        arguments?.apply {
            val type = this.getString(ARG_TYPE, resources.getString(R.string.type_movie))
            val id = this.getInt(ARG_ID)
            viewModel.fetchData(type, id).observe(viewLifecycleOwner, {
                if (it != null) {
                    viewModel.setLoading(false)
                    viewModel.setData(it)
                    with(binding) {
                        Glide.with(this@DetailFragment)
                            .load(it.posterUrl)
                            .apply(RequestOptions().override(
                                resources.getDimensionPixelSize(R.dimen.poster_width_large),
                                resources.getDimensionPixelSize(R.dimen.poster_height_large)
                            ))
                            .placeholder(R.color.colorPrimaryDark)
                            .error(R.color.colorPrimaryDark)
                            .into(binding.moviePoster)
                        movieTitle.text = it.name
                        movieDesc.text = it.desc
                        movieRating.text = it.voteAverage.toString()
                    }
                }
            })
        }
    }

}
