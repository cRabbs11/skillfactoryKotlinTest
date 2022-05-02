package com.ekochkov.base_rest

interface RemoteProvider {

    fun provideRemote() : TmdbAPI
}