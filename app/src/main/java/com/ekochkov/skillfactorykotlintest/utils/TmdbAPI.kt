package com.ekochkov.skillfactorykotlintest.utils

import com.ekochkov.skillfactorykotlintest.data.entity.PopularFilmsDataDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TmdbAPI {
    @GET("3/movie/popular")
    fun getFilms(
            @Query("api_key") apiKey: String,
            @Query("language") language: String,
            @Query("page") page: Int
    ): Call<PopularFilmsDataDTO>
}