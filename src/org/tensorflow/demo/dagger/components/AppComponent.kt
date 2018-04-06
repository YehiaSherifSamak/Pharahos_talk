package org.tensorflow.demo.dagger.components

import android.content.Context
import org.tensorflow.demo.API.LoggingService
import org.tensorflow.demo.dagger.modules.*
import org.tensorflow.demo.events.RxBus
import org.tensorflow.demo.utils.PreferencesHelper
import org.tensorflow.demo.utils.RatingHelper
import dagger.Component
import io.realm.Realm
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * Created by bridou_n on 05/10/2016.
 */
@Singleton
@Component(modules = arrayOf(
        ContextModule::class,
        DatabaseModule::class,
        NetworkModule::class,
        EventModule::class,
        PreferencesModule::class
))
interface AppComponent {
    fun context(): Context
    fun realm(): Realm
    fun loggingService(): LoggingService
    fun rxBus(): RxBus
    fun prefs(): PreferencesHelper
    fun rating(): RatingHelper
}
