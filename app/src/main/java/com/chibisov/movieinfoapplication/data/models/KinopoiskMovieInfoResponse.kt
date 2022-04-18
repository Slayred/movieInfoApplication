package com.chibisov.movieinfoapplication.data.models

import android.os.Parcelable
import com.chibisov.movieinfoapplication.R
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class KinopoiskMovieInfoResponse(

    @field:SerializedName("ratingImdb")
    val ratingImdb: Double? = null,

    @field:SerializedName("year")
    val year: Int? = null,

    @field:SerializedName("imdbId")
    val imdbId: String? = null,

    @field:SerializedName("filmLength")
    val filmLength: Int? = null,

    @field:SerializedName("description")
    val description: String,

    @field:SerializedName("reviewsCount")
    val reviewsCount: Int? = null,

    @field:SerializedName("ratingGoodReview")
    val ratingGoodReview: Double? = null,

    @field:SerializedName("type")
    val type: String? = null,

    @field:SerializedName("endYear")
    val endYear: String? = null,

    @field:SerializedName("ratingRfCriticsVoteCount")
    val ratingRfCriticsVoteCount: Int? = null,

    @field:SerializedName("hasImax")
    val hasImax: Boolean? = null,

    @field:SerializedName("nameRu")
    val nameRu: String,

    @field:SerializedName("lastSync")
    val lastSync: String? = null,

    @field:SerializedName("posterUrl")
    val posterUrl: String? = null,

    @field:SerializedName("genres")
    val genres: List<GenresItem?>? = null,

    @field:SerializedName("productionStatus")
    val productionStatus: String? = null,

    @field:SerializedName("isTicketsAvailable")
    val isTicketsAvailable: Boolean? = null,

    @field:SerializedName("ratingMpaa")
    val ratingMpaa: String? = null,

    @field:SerializedName("ratingAgeLimits")
    val ratingAgeLimits: String? = null,

    @field:SerializedName("editorAnnotation")
    val editorAnnotation: String? = null,

    @field:SerializedName("startYear")
    val startYear: String? = null,

    @field:SerializedName("ratingKinopoiskVoteCount")
    val ratingKinopoiskVoteCount: Int? = null,

    @field:SerializedName("nameEn")
    val nameEn: String? = null,

    @field:SerializedName("shortDescription")
    val shortDescription: String? = null,

    @field:SerializedName("countries")
    val countries: List<CountriesItem?>? = null,

    @field:SerializedName("completed")
    val completed: Boolean? = null,

    @field:SerializedName("ratingAwaitCount")
    val ratingAwaitCount: Int? = null,

    @field:SerializedName("has3D")
    val has3D: Boolean? = null,

    @field:SerializedName("logoUrl")
    val logoUrl: String? = null,

    @field:SerializedName("ratingKinopoisk")
    val ratingKinopoisk: Double? = null,

    @field:SerializedName("coverUrl")
    val coverUrl: String? = null,

    @field:SerializedName("nameOriginal")
    val nameOriginal: String? = null,

    @field:SerializedName("ratingGoodReviewVoteCount")
    val ratingGoodReviewVoteCount: Int? = null,

    @field:SerializedName("serial")
    val serial: Boolean? = null,

    @field:SerializedName("webUrl")
    val webUrl: String? = null,

    @field:SerializedName("posterUrlPreview")
    val posterUrlPreview: String? = null,

    @field:SerializedName("shortFilm")
    val shortFilm: Boolean? = null,

    @field:SerializedName("ratingRfCritics")
    val ratingRfCritics: Int? = null,

    @field:SerializedName("ratingImdbVoteCount")
    val ratingImdbVoteCount: Int? = null,

    @field:SerializedName("ratingAwait")
    val ratingAwait: String? = null,

    @field:SerializedName("ratingFilmCritics")
    val ratingFilmCritics: Double? = null,

    @field:SerializedName("slogan")
    val slogan: String? = null,

    @field:SerializedName("kinopoiskId")
    val kinopoiskId: Int,

    @field:SerializedName("ratingFilmCriticsVoteCount")
    val ratingFilmCriticsVoteCount: Int? = null
) : Parcelable {

    fun toUiMovie() = this.posterUrlPreview?.let {
        UiMovie(
        this.kinopoiskId,
        this.nameRu,
        this.description,
            it,
        status = false,
        checked = false
    )
    }
}


