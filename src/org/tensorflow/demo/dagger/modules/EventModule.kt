package org.tensorflow.demo.dagger.modules

import org.tensorflow.demo.events.RxBus

import javax.inject.Singleton

import dagger.Module
import dagger.Provides

/**
 * Created by bridou_n on 05/10/2016.
 */

@Module
class EventModule {
    @Provides
    @Singleton
    fun providesRxBus(): RxBus {
        return RxBus()
    }
}
