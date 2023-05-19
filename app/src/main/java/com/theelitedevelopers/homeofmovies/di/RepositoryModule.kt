package com.theelitedevelopers.homeofmovies.di
import com.theelitedevelopers.homeofmovies.domain.repository.MovieRepository
import com.theelitedevelopers.homeofmovies.domain.repository.MovieRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    @ViewModelScoped
    abstract fun bindsProductRepository(productRepositoryImpl: MovieRepositoryImpl) : MovieRepository
}