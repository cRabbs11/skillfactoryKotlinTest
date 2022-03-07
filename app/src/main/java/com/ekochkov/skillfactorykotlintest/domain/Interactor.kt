package com.ekochkov.skillfactorykotlintest.domain

import com.ekochkov.skillfactorykotlintest.data.FilmRepository
import com.ekochkov.skillfactorykotlintest.data.PreferenceProvider
import com.ekochkov.skillfactorykotlintest.data.entity.PopularFilmsDataDTO
import com.ekochkov.skillfactorykotlintest.utils.API
import com.ekochkov.skillfactorykotlintest.utils.Converter
import com.ekochkov.skillfactorykotlintest.utils.TmdbAPI
import com.ekochkov.skillfactorykotlintest.viewmodel.HomeFragmentViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Interactor(private val repository: FilmRepository, private val tmdbRetrofitService: TmdbAPI, private val preferenceProvider: PreferenceProvider) {
    fun getFilmsDB(): List<Film> = repository.filmList

    fun getFilmsFromTmdb(page: Int, callBack: HomeFragmentViewModel.ApiCallback) {
        tmdbRetrofitService.getFilms(preferenceProvider.getDefaultTypeCategory(), API.KEY, "ru-RU", page).enqueue(object: Callback<PopularFilmsDataDTO> {
            override fun onResponse(call: Call<PopularFilmsDataDTO>, response: Response<PopularFilmsDataDTO>) {
                callBack.onSuccess(Converter.convertTmdbListToDTOList(response.body()?.tmdbFilms))
            }

            override fun onFailure(call: Call<PopularFilmsDataDTO>, t: Throwable) {
                callBack.onFailure()
            }

        })

    }

    fun getDefaultTypeCategory(): String {
        return preferenceProvider.getDefaultTypeCategory()
    }

    fun setDefaultTypeCategory(typeCategory: String) {
        preferenceProvider.saveDefaultTypeCategory(typeCategory)
    }
}