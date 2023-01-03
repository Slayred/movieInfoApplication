package com.chibisov.movieinfoapplication

import android.app.Application
import com.chibisov.movieinfoapplication.data.MovieCacheDataSource
import com.chibisov.movieinfoapplication.data.MovieNetDataSource
import com.chibisov.movieinfoapplication.data.Repository
import com.chibisov.movieinfoapplication.data.converter.impl.MovieInfoConverterImpl
import com.chibisov.movieinfoapplication.data.converter.impl.MovieListConverterImpl
import com.chibisov.movieinfoapplication.data.local.MovieInfoDataBase
import com.chibisov.movieinfoapplication.data.local.dao.MovieInfoDao
import com.chibisov.movieinfoapplication.data.local.dao.MovieListDao
import com.chibisov.movieinfoapplication.data.net.MovieService
import com.chibisov.movieinfoapplication.data.repositories.MovieInfoRepository
import com.chibisov.movieinfoapplication.data.repositories.impl.MovieInfoRepositoryImpl
import com.chibisov.movieinfoapplication.data.retrofit.RetrofitFactory
import com.chibisov.movieinfoapplication.domain.Communication
import com.chibisov.movieinfoapplication.domain.interactor.BaseInteractor
import com.chibisov.movieinfoapplication.domain.interactor.MovieInfoInteractor
import com.chibisov.movieinfoapplication.domain.interactor.impl.MovieInfoInteractorImpl
import com.chibisov.movieinfoapplication.viewmodels.FavoriteMovieListViewModel
import com.chibisov.movieinfoapplication.viewmodels.MovieListViewModel
import com.chibisov.movieinfoapplication.viewmodels.SharedMovieViewModel


class MovieInfoApp : Application() {

    lateinit var movieListViewModel: MovieListViewModel
    lateinit var movieFavoriteListViewModel: FavoriteMovieListViewModel
    lateinit var sharedMovieViewModel: SharedMovieViewModel
//    val homeViewModel: HomeViewModel  by viewModels()

    private lateinit var movieInfoInteractor: MovieInfoInteractor
    private lateinit var movieRepository: Repository
    private lateinit var movieInteractor: BaseInteractor
    private lateinit var movieNetDataSource: MovieNetDataSource
    private lateinit var movieInfoRepository: MovieInfoRepository
    private val BASE_URL = "https://kinopoiskapiunofficial.tech/api/v2.2/"

    private lateinit var movieCacheDataSource: MovieCacheDataSource
    private lateinit var movieListDao: MovieListDao
    private lateinit var movieInfoDao: MovieInfoDao

    override fun onCreate() {
        super.onCreate()
        movieNetDataSource = MovieNetDataSource(
            RetrofitFactory.getRetrofitInstance(BASE_URL).create(MovieService::class.java)
        )
        movieListDao = MovieInfoDataBase.getDataBase(this).movieListDao()
        movieInfoDao = MovieInfoDataBase.getDataBase(this).movieInfoData()
        movieCacheDataSource = MovieCacheDataSource(movieListDao, movieInfoDao)
        movieInfoRepository = MovieInfoRepositoryImpl(movieNetDataSource,movieCacheDataSource)
        movieRepository = Repository(
            movieCacheDataSource,
            movieNetDataSource,
            MovieInfoConverterImpl(),
            MovieListConverterImpl()
        )
        movieInteractor = BaseInteractor(movieRepository)
        movieInfoInteractor = MovieInfoInteractorImpl(movieInfoRepository, MovieInfoConverterImpl())
        movieListViewModel = MovieListViewModel(Communication(), movieInteractor)
        movieFavoriteListViewModel = FavoriteMovieListViewModel(Communication(), movieInteractor)
        sharedMovieViewModel = SharedMovieViewModel(movieInfoInteractor)
    }
}