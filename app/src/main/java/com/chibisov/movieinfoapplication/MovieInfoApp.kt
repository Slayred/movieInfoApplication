package com.chibisov.movieinfoapplication

import android.app.Application
import com.chibisov.movieinfoapplication.data.MovieNetDataSource
import com.chibisov.movieinfoapplication.data.MoviesCacheFavorites
import com.chibisov.movieinfoapplication.data.Repository
import com.chibisov.movieinfoapplication.data.net.MovieService
import com.chibisov.movieinfoapplication.data.retrofit.RetrofitFactory
import com.chibisov.movieinfoapplication.domain.BaseInteractor
import com.chibisov.movieinfoapplication.domain.Communication
import com.chibisov.movieinfoapplication.domain.MovieInteractor
import com.chibisov.movieinfoapplication.viewmodels.FavoriteMovieListViewModel
import com.chibisov.movieinfoapplication.viewmodels.MovieListViewModel
import com.chibisov.movieinfoapplication.viewmodels.SharedMovieViewModel

class MovieInfoApp : Application() {

    lateinit var movieListViewModel: MovieListViewModel
    lateinit var movieFavoriteListViewModel: FavoriteMovieListViewModel
    lateinit var sharedMovieViewModel: SharedMovieViewModel
    private lateinit var movieInfoInteractor: MovieInteractor
    private lateinit var movieRepository: Repository
    private lateinit var movieInteractor: BaseInteractor
    private lateinit var movieNetDataSource: MovieNetDataSource
    var BASE_URL = "https://kinopoiskapiunofficial.tech/api/v2.2/"

    override fun onCreate() {
        super.onCreate()
        movieNetDataSource = MovieNetDataSource(
            RetrofitFactory.getRetrofitInstance(BASE_URL).create(MovieService::class.java)
        )
        movieRepository = Repository(MoviesCacheFavorites, movieNetDataSource)
        movieInteractor = BaseInteractor(movieRepository)
        movieInfoInteractor = MovieInteractor(movieRepository)
        movieListViewModel = MovieListViewModel(Communication(), movieInteractor)
        movieFavoriteListViewModel = FavoriteMovieListViewModel(Communication(), movieInteractor)
        sharedMovieViewModel = SharedMovieViewModel(movieInfoInteractor)
    }
}