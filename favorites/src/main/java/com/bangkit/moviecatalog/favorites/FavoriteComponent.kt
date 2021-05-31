package com.bangkit.moviecatalog.favorites

import android.content.Context
import com.bangkit.moviecatalog.di.FavoriteModuleDependencies
import com.bangkit.moviecatalog.favorites.ui.FavoritesFragment
import dagger.BindsInstance
import dagger.Component

@Component(dependencies = [FavoriteModuleDependencies::class])
interface FavoriteComponent {
    fun inject(fragment: FavoritesFragment)

    @Component.Builder
    interface Builder {
        fun context(@BindsInstance context: Context): Builder
        fun appDependencies(mapsModuleDependencies: FavoriteModuleDependencies): Builder
        fun build(): FavoriteComponent
    }
}