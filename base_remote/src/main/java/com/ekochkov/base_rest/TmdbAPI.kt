package com.ekochkov.base_rest

import com.ekochkov.base_rest.entity.PopularFilmsDataDTO
import io.reactivex.rxjava3.core.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbAPI {
    @GET("3/movie/{path}")
    fun getFilms(
            @Path("path") movie_path: String,
            @Query("api_key") apiKey: String,
            @Query("language") language: String,
            @Query("page") page: Int,
    ): Observable<PopularFilmsDataDTO>

    @GET("3/search/movie")
    fun searchFilmsByObservable(
        @Query("api_key") apiKey: String,
        @Query("query") query: String,
        @Query("language") language: String,
        @Query("page") page: Int,
    ): Observable<PopularFilmsDataDTO>
}