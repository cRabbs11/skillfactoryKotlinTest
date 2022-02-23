package com.ekochkov.skillfactorykotlintest.utils

import javax.inject.Inject

class BindsTestClass
@Inject constructor() : BindsTestInterface {
    override fun doSomething() {
        println("doSomething in bindsTestClass")
    }
}