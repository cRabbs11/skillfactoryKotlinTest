package com.ekochkov.skillfactorykotlintest.di

import com.ekochkov.base_rest.RemoteProvider
import com.ekochkov.skillfactorykotlintest.di.modules.DatabaseModule
import com.ekochkov.skillfactorykotlintest.di.modules.DomainModule
import com.ekochkov.skillfactorykotlintest.di.modules.TestModule
import com.ekochkov.skillfactorykotlintest.viewmodel.FavoritesFragmentViewModel
import com.ekochkov.skillfactorykotlintest.viewmodel.HomeFragmentViewModel
import com.ekochkov.skillfactorykotlintest.viewmodel.SettingsFragmentViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    dependencies = [RemoteProvider::class],
    modules = [
        DatabaseModule::class,
        DomainModule::class,
        TestModule::class
    ]
)

interface AppComponent {

    fun inject(homeFragmentViewModel: HomeFragmentViewModel)
    fun inject(favoritesFragmentViewModel: FavoritesFragmentViewModel)
    fun inject(settingsFragment: SettingsFragmentViewModel)
}