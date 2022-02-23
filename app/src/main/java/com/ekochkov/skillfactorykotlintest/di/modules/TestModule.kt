package com.ekochkov.skillfactorykotlintest.di.modules

import com.ekochkov.skillfactorykotlintest.utils.BindsTestClass
import com.ekochkov.skillfactorykotlintest.utils.BindsTestInterface
import dagger.Binds
import dagger.Module

@Module
abstract class TestModule {

    @Binds
    abstract fun provideBindTestClass(bindsTestClass: BindsTestClass): BindsTestInterface
}