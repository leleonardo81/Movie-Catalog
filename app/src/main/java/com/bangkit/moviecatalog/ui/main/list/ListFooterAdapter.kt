package com.bangkit.moviecatalog.ui.main.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.moviecatalog.databinding.ListFooterBinding

class ListFooterAdapter (private val retry: () -> Unit) : LoadStateAdapter<ListFooterAdapter.Holder>() {
    override fun onBindViewHolder(holder: Holder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): Holder {
        return Holder.create(parent, retry)
    }

    class Holder(private val binding: ListFooterBinding,
                       retry: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.retryButton.setOnClickListener { retry.invoke() }
        }

        fun bind(loadState: LoadState) {
            if (loadState is LoadState.Error) {
                binding.errorMsg.text = loadState.error.localizedMessage
            }
            binding.progressBar.isVisible = loadState is LoadState.Loading
            binding.retryButton.isVisible = loadState is LoadState.Error
            binding.errorMsg.isVisible = loadState is LoadState.Error
        }

        companion object {
            fun create(parent: ViewGroup, retry: () -> Unit): Holder {
                val binding = ListFooterBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false)
                return Holder(binding, retry)
            }
        }
    }
}