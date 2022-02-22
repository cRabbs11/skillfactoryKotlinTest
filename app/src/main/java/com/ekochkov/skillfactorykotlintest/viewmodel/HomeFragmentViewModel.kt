package com.ekochkov.skillfactorykotlintest.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ekochkov.skillfactorykotlintest.App
import com.ekochkov.skillfactorykotlintest.domain.Film

class HomeFragmentViewModel: ViewModel() {
    val filmListLiveData = MutableLiveData<List<Film>>()
    private var interactor = App.instance.interactor

    init {
        val films = interactor.getFilmsDB()
        filmListLiveData.postValue(films)
    }
}