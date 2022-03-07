package com.ekochkov.skillfactorykotlintest.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ekochkov.skillfactorykotlintest.App
import com.ekochkov.skillfactorykotlintest.domain.Interactor
import javax.inject.Inject

class SettingsFragmentViewModel: ViewModel() {

    val typeCategoryLifeData = MutableLiveData<String>()

    @Inject
    lateinit var interactor : Interactor

    init {
        App.instance.dagger.inject(this)

        getSavedTypeCategory()
    }

    private fun getSavedTypeCategory() {
        typeCategoryLifeData.value = interactor.getDefaultTypeCategory()
    }

    fun setTypeCategory(typeCategory: String) {
        interactor.setDefaultTypeCategory(typeCategory)
        getSavedTypeCategory()
    }


}