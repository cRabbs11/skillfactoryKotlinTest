package com.ekochkov.skillfactorykotlintest

import android.app.Application
import com.ekochkov.skillfactorykotlintest.di.AppComponent
import com.ekochkov.skillfactorykotlintest.di.DaggerAppComponent
import com.ekochkov.skillfactorykotlintest.di.modules.DatabaseModule
import com.ekochkov.skillfactorykotlintest.di.modules.DomainModule
import com.ekochkov.skillfactorykotlintest.di.modules.RemoteModule


class App: Application() {
    lateinit var dagger: AppComponent

    override fun onCreate() {
        super.onCreate()
        instance = this
        dagger = DaggerAppComponent.builder()
            .databaseModule(DatabaseModule())
            .domainModule(DomainModule(this))
            .remoteModule(RemoteModule())
            .build()
    }

    companion object {
        //Здесь статически хранится ссылка на экземпляр App
        lateinit var instance: App
            //Приватный сеттер, чтобы нельзя было в эту переменную присвоить что-либо другое
            private set
    }
}