package com.ekochkov.skillfactorykotlintest.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ekochkov.skillfactorykotlintest.App
import com.ekochkov.skillfactorykotlintest.domain.Film
import com.ekochkov.skillfactorykotlintest.domain.Interactor
import javax.inject.Inject

class FavoritesFragmentViewModel: ViewModel() {
    val filmListLiveData = MutableLiveData<List<Film>>()

    @Inject
    lateinit var interactor : Interactor

    init {
        App.instance.dagger.inject(this)
        val films = interactor.getFilmsFromDB()
        filmListLiveData.postValue(films)
    }
}