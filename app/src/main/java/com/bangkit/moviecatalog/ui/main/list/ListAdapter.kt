package com.bangkit.moviecatalog.ui.main.list

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.moviecatalog.R
import com.bangkit.moviecatalog.data.source.local.entity.MovieModel
import com.bangkit.moviecatalog.databinding.ListItemBinding
import com.bangkit.moviecatalog.ui.detail.DetailActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class ListAdapter: PagingDataAdapter<MovieModel, ListAdapter.Holder>(DIFF_CALLBACK) {
    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MovieModel>() {
            override fun areItemsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean {
                return oldItem.id == newItem.id
            }
            override fun areContentsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class Holder(private val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movieModel: MovieModel) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(movieModel.posterUrl)
                    .apply(
                        RequestOptions().override(
                            itemView.context.resources.getDimensionPixelSize(R.dimen.poster_width_small),
                            itemView.context.resources.getDimensionPixelSize(R.dimen.poster_height_small)
                        )
                    )
                    .placeholder(R.color.colorPrimaryDark)
                    .error(R.color.colorPrimaryDark)
                    .into(binding.moviePoster)
                movieTitle.text = movieModel.name
                movieDesc.text = movieModel.desc
                movieRating.text = movieModel.voteAverage.toString()

                itemView.setOnClickListener {
                    val detailIntent = Intent(itemView.context, DetailActivity::class.java)
                    detailIntent.apply {
                        putExtra(DetailActivity.EXTRA_ID, movieModel.id)
                        putExtra(DetailActivity.EXTRA_TYPE, movieModel.type)
                    }
                    itemView.context.startActivity(detailIntent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val itemBinding = ListItemBinding.inflate(LayoutInflater
            .from(parent.context), parent, false)
        return Holder(itemBinding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = getItem(position)
        if (item != null) holder.bind(item)
    }

}