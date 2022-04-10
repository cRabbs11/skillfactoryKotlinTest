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
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.functions.Consumer
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class HomeFragmentViewModel: ViewModel() {
    val filmListLiveData = MutableLiveData<List<Film>>()
    val loadingProgressLiveData = MutableLiveData<Boolean>()
    val toastEventLiveData = SingleLiveEvent<String>()
    private var tmdbFilmListPage = 1
    private val INVISIBLE_FILMS_UNTIL_NEW_REQUEST = 2
    private var isWaitingRequest = false
    val compositeDisposable = CompositeDisposable()

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

        val completable = Completable
                .fromAction { getFilmsFromTmdb() }
                .subscribeOn(Schedulers.io())
                .subscribe()
        compositeDisposable.add(completable)


        val disposible = interactor.getFilmsFromDBAsObservable().subscribe(object: Consumer<List<Film>> {
            override fun accept(t: List<Film>?) {
                filmListLiveData.postValue(t)
            }
        })
        compositeDisposable.add(disposible)
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
        val completable = Completable.fromAction {
            tmdbFilmListPage = 1
            interactor.removeAllFilmsInDB()
            getFilmsFromTmdb()
        }.subscribeOn(Schedulers.io())
                .subscribe()
        compositeDisposable.add(completable)
    }

    fun getLastVisibleFilmInList(lastVisible: Int) {
        val completable = Completable.fromAction {
            val filmListSize = filmListLiveData.value?.size ?: 0
            if ((filmListSize - INVISIBLE_FILMS_UNTIL_NEW_REQUEST) <= lastVisible && !isWaitingRequest) {
                if (BuildConfig.DEBUG) {
                    Log.d("TAG", "new getFilms request")
                }
                getFilmsFromTmdb()
            }
        }.subscribeOn(Schedulers.io())
                .subscribe()
        compositeDisposable.add(completable)
    }

    override fun onCleared() {
        interactor.unregisterPrefListener(prefListener)
        compositeDisposable.dispose()
        super.onCleared()
    }

    interface ApiCallback {
        fun onSuccess()
        fun onFailure()
    }
}