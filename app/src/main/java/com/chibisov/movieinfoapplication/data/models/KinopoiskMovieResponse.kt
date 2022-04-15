package com.chibisov.movieinfoapplication.data.models

import android.os.Parcelable
import com.chibisov.movieinfoapplication.R
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class KinopoiskMovieResponse(

    @field:SerializedName("films")
    val films: List<FilmsItem?>? = null,

    @field:SerializedName("pagesCount")
    val pagesCount: Int? = null
) : Parcelable

@Parcelize
data class FilmsItem(

    @field:SerializedName("nameRu")
    val nameRu: String,

    @field:SerializedName("ratingVoteCount")
    val ratingVoteCount: Int? = null,

    @field:SerializedName("posterUrl")
    val posterUrl: String? = null,

    @field:SerializedName("year")
    val year: String? = null,

    @field:SerializedName("genres")
    val genres: List<GenresItem?>? = null,

    @field:SerializedName("ratingChange")
    val ratingChange: Boolean? = null,

    @field:SerializedName("filmId")
    val filmId: Int,

    @field:SerializedName("filmLength")
    val filmLength: String? = null,

    @field:SerializedName("rating")
    val rating: String? = null,

    @field:SerializedName("posterUrlPreview")
    val posterUrlPreview: String? = null,

    @field:SerializedName("nameEn")
    val nameEn: String? = null,

    @field:SerializedName("countries")
    val countries: List<CountriesItem?>? = null
) : Parcelable {

    fun toUiMovie() = UiMovie(
        this.filmId,
        this.nameRu,
        "desct",
        R.drawable.ic_fight_club_foreground,
        status = false,
        checked = false
    )
}

@Parcelize
data class GenresItem(

    @field:SerializedName("genre")
    val genre: String? = null
) : Parcelable

@Parcelize
data class CountriesItem(

    @field:SerializedName("country")
    val country: String? = null
) : Parcelable
