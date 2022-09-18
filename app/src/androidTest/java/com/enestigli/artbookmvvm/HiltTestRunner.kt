package com.enestigli.artbookmvvm

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import dagger.hilt.android.testing.HiltTestApplication

class HiltTestRunner :AndroidJUnitRunner() {

    //hilti test etmek için bu şekilde bu sınıfı yazmamız gerekiyor. bunlar dökümantasyonda var .
    override fun newApplication(
        cl: ClassLoader?,
        className: String?,
        context: Context?
    ): Application {
        return super.newApplication(cl, HiltTestApplication::class.java.name, context)
    }
}