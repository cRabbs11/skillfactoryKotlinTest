package com.ekochkov.skillfactorykotlintest.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ekochkov.skillfactorykotlintest.App
import com.ekochkov.skillfactorykotlintest.data.entity.Film
import com.ekochkov.skillfactorykotlintest.domain.Interactor
import javax.inject.Inject

class FavoritesFragmentViewModel: ViewModel() {
    val filmListLiveData : LiveData<List<Film>>

    @Inject
    lateinit var interactor : Interactor

    init {
        App.instance.dagger.inject(this)
        filmListLiveData = interactor.getFilmsFromDB()
    }
}