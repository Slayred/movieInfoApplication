package com.chibisov.movieinfoapplication

import android.app.Application
import com.chibisov.movieinfoapplication.data.Movies
import com.chibisov.movieinfoapplication.data.MoviesCacheFavorites
import com.chibisov.movieinfoapplication.data.Repository
import com.chibisov.movieinfoapplication.domain.BaseInteractor
import com.chibisov.movieinfoapplication.domain.Communication
import com.chibisov.movieinfoapplication.viewmodels.BaseViewModel
import com.chibisov.movieinfoapplication.viewmodels.MovieListViewModel

class MovieInfoApp: Application() {

    lateinit var movieListViewModel: MovieListViewModel
    private lateinit var movieCommunication: Communication
    private lateinit var movieRepository: Repository
    private lateinit var movieInteractor: BaseInteractor

    override fun onCreate() {
        super.onCreate()
        movieCommunication = Communication()
        movieRepository = Repository(MoviesCacheFavorites, Movies)
        movieInteractor = BaseInteractor(movieRepository)
        movieListViewModel = MovieListViewModel(movieCommunication, movieInteractor)
    }
}