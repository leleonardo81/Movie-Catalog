package com.bangkit.moviecatalog.ui.detail

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bangkit.moviecatalog.R
import com.bangkit.moviecatalog.databinding.DetailActivityBinding
import com.bangkit.moviecatalog.ui.detail.viewmodel.DetailViewModel
import com.bangkit.moviecatalog.viewmodel.ViewModelFactory

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ID = "extra_id"
        const val EXTRA_TYPE = "extra_type"
    }

    private lateinit var binding: DetailActivityBinding
    private lateinit var viewModel: DetailViewModel
    private var menu: Menu? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DetailActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this,
            ViewModelFactory.getInstance(this))[DetailViewModel::class.java]

        viewModel.data.observe(this, {
            menu?.apply {
                when (it.isFavorite) {
                    true -> this.getItem(0).setIcon(R.drawable.ic_favorite_24dp)
                    false -> this.getItem(0).setIcon(R.drawable.ic_favorite_border_24dp)
                }
            }
        })

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val bundle = intent.extras
        if (savedInstanceState == null && bundle != null) {
            supportFragmentManager.beginTransaction()
                .replace(binding.container.id, DetailFragment.newInstance(
                    bundle.getInt(EXTRA_ID),
                    bundle.getString(EXTRA_TYPE, resources.getString(R.string.type_movie))
                ))
                .commitNow()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        super.onBackPressed()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        this.menu = menu
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when(item.itemId) {
        R.id.set_favorite_menu -> {
            viewModel.data.value?.isFavorite?.let {
                if (it) {
                    item.setIcon(R.drawable.ic_favorite_border_24dp)
                    viewModel.setFavorite(!it)
                } else {
                    item.setIcon(R.drawable.ic_favorite_24dp)
                    viewModel.setFavorite(!it)
                }
                return true
            }
            false
        }
        else -> super.onOptionsItemSelected(item)
    }
}
