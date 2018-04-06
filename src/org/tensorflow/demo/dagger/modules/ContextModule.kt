package org.tensorflow.demo.dagger.modules

import android.app.Application
import android.content.Context

import javax.inject.Singleton

import dagger.Module
import dagger.Provides

@Module
class ContextModule(private val context: Application) {

    @Provides
    @Singleton
    fun providesContext(): Context {
        return context
    }
}
