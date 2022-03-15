package com.ekochkov.skillfactorykotlintest.domain

import android.content.SharedPreferences
import com.ekochkov.skillfactorykotlintest.data.FilmRepository
import com.ekochkov.skillfactorykotlintest.data.PreferenceProvider
import com.ekochkov.skillfactorykotlintest.data.entity.Film
import com.ekochkov.skillfactorykotlintest.data.entity.PopularFilmsDataDTO
import com.ekochkov.skillfactorykotlintest.utils.API
import com.ekochkov.skillfactorykotlintest.utils.Converter
import com.ekochkov.skillfactorykotlintest.utils.TmdbAPI
import com.ekochkov.skillfactorykotlintest.viewmodel.HomeFragmentViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Interactor(private val repository: FilmRepository, private val tmdbRetrofitService: TmdbAPI, private val preferenceProvider: PreferenceProvider) {

    fun getFilmsFromDB(): List<Film> {
        return repository.getAllFilmsFromDB()
    }

    fun putFilmInBd(film: Film) {
        repository.putFilmInDB(film)
    }

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

    fun registerPrefListener(listener: SharedPreferences.OnSharedPreferenceChangeListener) {
        preferenceProvider.registerPrefListener(listener)
    }

    fun unregisterPrefListener(listener: SharedPreferences.OnSharedPreferenceChangeListener) {
        preferenceProvider.unregisterPrefListener(listener)
    }
}