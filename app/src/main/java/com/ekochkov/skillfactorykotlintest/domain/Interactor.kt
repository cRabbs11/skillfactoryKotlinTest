package com.ekochkov.skillfactorykotlintest.domain

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import com.ekochkov.base_rest.TmdbAPI
import com.ekochkov.skillfactorykotlintest.data.FilmRepository
import com.ekochkov.skillfactorykotlintest.data.PreferenceProvider
import com.ekochkov.skillfactorykotlintest.data.entity.Film
import com.ekochkov.skillfactorykotlintest.utils.API
import com.ekochkov.skillfactorykotlintest.utils.Converter
import com.ekochkov.skillfactorykotlintest.viewmodel.HomeFragmentViewModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers

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
        val observable = tmdbRetrofitService.getFilms(preferenceProvider.getDefaultTypeCategory(), API.KEY, "ru-RU", page)
            .subscribeOn(Schedulers.io())
            .map {
                Converter.convertTmdbListToDTOList(it.tmdbFilms)
            }
            .subscribeBy(
                onError = {
                    println("error: ${it.printStackTrace()}")
                    callBack.onFailure()
                },
                onNext = { list ->
                    repository.putFilmsInDB(list)
                    callBack.onSuccess()
                }
            )
    }

    fun searchFilmsFromTmdb(query: String, page: Int, callback: HomeFragmentViewModel.ApiSearchCallback) {
        val observable = tmdbRetrofitService.searchFilmsByObservable(API.KEY, query, "ru-RU", page)
            .subscribeOn(Schedulers.io())
            .map {
                Converter.convertTmdbListToDTOList(it.tmdbFilms)
            }
            .subscribe {
                callback.onSuccess(it)
            }
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