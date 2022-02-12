package com.ekochkov.skillfactorykotlintest.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ekochkov.skillfactorykotlintest.App
import com.ekochkov.skillfactorykotlintest.domain.Film

class HomeFragmentViewModel: ViewModel() {
    val filmListLiveData = MutableLiveData<List<Film>>()
    private var tmdbFilmListPage = 1
    private val INVISIBLE_FILMS_UNTIL_NEW_REQUEST = 1
    private var interactor = App.instance.interactor
    private var filmListSize = 0
    private var isWaitingRequest = false

    init {
        //val films = interactor.getFilmsDB()
        interactor.getFilmsFromTmdb(tmdbFilmListPage, object: ApiCallback {
            override fun onSuccess(films: List<Film>) {
                filmListLiveData.postValue(films)
            }

            override fun onFailure() {

            }

        })

    }

    interface ApiCallback {
        fun onSuccess(films: List<Film>)
        fun onFailure()
    }
}