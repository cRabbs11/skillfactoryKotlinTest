package com.ekochkov.skillfactorykotlintest.di

import com.ekochkov.skillfactorykotlintest.di.modules.DatabaseModule
import com.ekochkov.skillfactorykotlintest.di.modules.DomainModule
import com.ekochkov.skillfactorykotlintest.di.modules.RemoteModule
import com.ekochkov.skillfactorykotlintest.di.modules.TestModule
import com.ekochkov.skillfactorykotlintest.viewmodel.FavoritesFragmentViewModel
import com.ekochkov.skillfactorykotlintest.viewmodel.HomeFragmentViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        DatabaseModule::class,
        RemoteModule::class,
        DomainModule::class,
        TestModule::class
    ]
)

interface AppComponent {

    fun inject(homeFragmentViewModel: HomeFragmentViewModel)
    fun inject(favoritesFragmentViewModel: FavoritesFragmentViewModel)
}