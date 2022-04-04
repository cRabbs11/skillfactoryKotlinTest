package com.ekochkov.skillfactorykotlintest.viewmodel

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ekochkov.skillfactorykotlintest.App
import com.ekochkov.skillfactorykotlintest.BuildConfig
import com.ekochkov.skillfactorykotlintest.data.KEY_DEFAULT_TYPE_CATEGORY
import com.ekochkov.skillfactorykotlintest.data.entity.Film
import com.ekochkov.skillfactorykotlintest.domain.Interactor
import com.ekochkov.skillfactorykotlintest.utils.BindsTestInterface
import com.ekochkov.skillfactorykotlintest.utils.SingleLiveEvent
import com.ekochkov.skillfactorykotlintest.utils.TmdbApiConstants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeFragmentViewModel: ViewModel() {
    val filmListLiveData = MutableLiveData<List<Film>>()
    val loadingProgressLiveData = MutableLiveData<Boolean>()
    val toastEventLiveData = SingleLiveEvent<String>()
    private var tmdbFilmListPage = 1
    private val INVISIBLE_FILMS_UNTIL_NEW_REQUEST = 2
    private var isWaitingRequest = false
    val homeFragmentScope = CoroutineScope(Dispatchers.IO)

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
        setChangeTypeCategoryListener()
        testClass.doSomething()


        homeFragmentScope.launch {
            getFilmsFromTmdb()
            interactor.getFilmsFromDBAsFlow().collect {list ->
                filmListLiveData.postValue(list)
            }
        }
    }

    private fun getFilmsFromTmdb() {
        if (!isWaitingRequest) {
            isWaitingRequest = true
            loadingProgressLiveData.postValue(isWaitingRequest)
            interactor.getFilmsFromTmdb(tmdbFilmListPage, object: ApiCallback {
                override fun onSuccess() {
                    tmdbFilmListPage++
                    isWaitingRequest = false
                    loadingProgressLiveData.postValue(isWaitingRequest)

                }

                override fun onFailure() {
                    isWaitingRequest = false
                    loadingProgressLiveData.postValue(isWaitingRequest)
                    toastEventLiveData.postValue(TmdbApiConstants.RESPONSE_ON_FAILURE)
                }
            })
        }
    }

    private fun setChangeTypeCategoryListener() {
        interactor.registerPrefListener(prefListener)
    }

    fun refreshFilms() {
        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch {
            tmdbFilmListPage = 1
            interactor.removeAllFilmsInDB()
            getFilmsFromTmdb()
        }
    }

    fun getLastVisibleFilmInList(lastVisible: Int) {
        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch {
            val filmListSize = filmListLiveData.value?.size ?: 0
            if ((filmListSize - INVISIBLE_FILMS_UNTIL_NEW_REQUEST) <= lastVisible && !isWaitingRequest) {
                if (BuildConfig.DEBUG) {
                    Log.d("TAG", "new getFilms request")
                }
                getFilmsFromTmdb()
            }
        }
    }

    override fun onCleared() {
        interactor.unregisterPrefListener(prefListener)
        super.onCleared()
    }

    interface ApiCallback {
        fun onSuccess()
        fun onFailure()
    }
}