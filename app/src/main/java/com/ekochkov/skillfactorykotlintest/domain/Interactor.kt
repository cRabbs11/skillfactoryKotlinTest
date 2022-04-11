package com.ekochkov.skillfactorykotlintest.domain

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import com.ekochkov.skillfactorykotlintest.data.FilmRepository
import com.ekochkov.skillfactorykotlintest.data.PreferenceProvider
import com.ekochkov.skillfactorykotlintest.data.entity.Film
import com.ekochkov.skillfactorykotlintest.data.entity.PopularFilmsDataDTO
import com.ekochkov.skillfactorykotlintest.utils.API
import com.ekochkov.skillfactorykotlintest.utils.Converter
import com.ekochkov.skillfactorykotlintest.utils.TmdbAPI
import com.ekochkov.skillfactorykotlintest.viewmodel.HomeFragmentViewModel
    import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Interactor(private val repository: FilmRepository, private val tmdbRetrofitService: TmdbAPI, private val preferenceProvider: PreferenceProvider) {

    fun getFilmsFromDB(): LiveData<List<Film>> {
        return repository.getAllFilmsFromDB()
    }

    fun getFilmsFromDBAsObservable() : Observable<List<Film>> {
        return repository.getAllFilmsFromBDAsObservable()
    }

    fun putFilmInBd(film: Film) {
        repository.putFilmInDB(film)
    }

    fun removeAllFilmsInDB() {
        repository.deleteAllFilmsInDB()
    }

    fun getFilmsFromTmdb(page: Int, callBack: HomeFragmentViewModel.ApiCallback) {
        tmdbRetrofitService.getFilms(preferenceProvider.getDefaultTypeCategory(), API.KEY, "ru-RU", page).enqueue(object: Callback<PopularFilmsDataDTO> {
            override fun onResponse(call: Call<PopularFilmsDataDTO>, response: Response<PopularFilmsDataDTO>) {
                callBack.onSuccess()

                val disposible = Single.just(response.body()?.tmdbFilms)
                        .subscribeOn(Schedulers.io())
                        .map { Converter.convertTmdbListToDTOList(it) }
                        .subscribe { list -> repository.putFilmsInDB(list)
                }
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