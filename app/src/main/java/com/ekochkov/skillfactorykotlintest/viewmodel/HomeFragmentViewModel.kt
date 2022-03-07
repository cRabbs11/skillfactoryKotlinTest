package com.ekochkov.skillfactorykotlintest.viewmodel

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ekochkov.skillfactorykotlintest.App
import com.ekochkov.skillfactorykotlintest.BuildConfig
import com.ekochkov.skillfactorykotlintest.data.KEY_DEFAULT_TYPE_CATEGORY
import com.ekochkov.skillfactorykotlintest.domain.Film
import com.ekochkov.skillfactorykotlintest.domain.Interactor
import com.ekochkov.skillfactorykotlintest.utils.BindsTestInterface
import javax.inject.Inject

class HomeFragmentViewModel: ViewModel() {
    val filmListLiveData = MutableLiveData<List<Film>>()
    private var tmdbFilmListPage = 1
    private val INVISIBLE_FILMS_UNTIL_NEW_REQUEST = 2
    private var filmListSize = 0
    private var isWaitingRequest = false

    private val prefListener = SharedPreferences.OnSharedPreferenceChangeListener { _: SharedPreferences, key: String ->
        when (key) {
            KEY_DEFAULT_TYPE_CATEGORY -> {
                refreshFilms()
            }
        }
    }


    @Inject
    lateinit var interactor : Interactor

    @Inject
    lateinit var testClass: BindsTestInterface

    init {
        App.instance.dagger.inject(this)
        //val films = interactor.getFilmsDB()
        setChangeTypeCategoryListener()
        testClass.doSomething()
        getFilmsFromTmdb()
    }

    private fun getFilmsFromTmdb() {
        isWaitingRequest = true
        interactor.getFilmsFromTmdb(tmdbFilmListPage, object: ApiCallback {
            override fun onSuccess(films: List<Film>) {
                tmdbFilmListPage++
                val oldList = filmListLiveData.value
                val newList = if (oldList!=null) {
                    oldList + films
                } else { films }
                filmListLiveData.postValue(newList)
                filmListSize = newList.size
                isWaitingRequest = false
            }

            override fun onFailure() {
                isWaitingRequest = false
            }
        })
    }

    private fun setChangeTypeCategoryListener() {
        interactor.registerPrefListener(prefListener)
    }

    fun refreshFilms() {
        filmListLiveData.postValue(listOf())
        tmdbFilmListPage = 1
        getFilmsFromTmdb()
    }

    fun getLastVisibleFilmInList(lastVisible: Int) {
        if (filmListSize-INVISIBLE_FILMS_UNTIL_NEW_REQUEST<=lastVisible && !isWaitingRequest) {
            if (BuildConfig.DEBUG) {
                Log.d("TAG", "new getFilms request")
            }
            getFilmsFromTmdb()
        }
    }

    override fun onCleared() {
        interactor.unregisterPrefListener(prefListener)
        super.onCleared()
    }

    interface ApiCallback {
        fun onSuccess(films: List<Film>)
        fun onFailure()
    }
}