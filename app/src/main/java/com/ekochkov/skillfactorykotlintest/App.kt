package com.ekochkov.skillfactorykotlintest

import android.app.Application
import com.ekochkov.skillfactorykotlintest.di.AppComponent
import com.ekochkov.skillfactorykotlintest.di.DaggerAppComponent


class App: Application() {
    lateinit var dagger: AppComponent

    override fun onCreate() {
        super.onCreate()
        instance = this
        dagger = DaggerAppComponent.create()
    }

    companion object {
        //Здесь статически хранится ссылка на экземпляр App
        lateinit var instance: App
            //Приватный сеттер, чтобы нельзя было в эту переменную присвоить что-либо другое
            private set
    }
}