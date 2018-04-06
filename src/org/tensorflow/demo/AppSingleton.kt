package org.tensorflow.demo

import android.app.Application
import org.tensorflow.demo.dagger.components.ActivityComponent
import org.tensorflow.demo.dagger.components.DaggerActivityComponent
import org.tensorflow.demo.dagger.components.DaggerAppComponent
import org.tensorflow.demo.dagger.modules.*
import org.tensorflow.demo.utils.RatingHelper
import io.realm.Realm
import io.realm.RealmConfiguration
import javax.inject.Inject

/**
 * Created by bridou_n on 30/09/2016.
 */

class AppSingleton : Application() {

    companion object {
        lateinit var activityComponent: ActivityComponent
    }

    @Inject lateinit var ratingHelper: RatingHelper

    override fun onCreate() {
        super.onCreate()

        val appComponent = DaggerAppComponent.builder()
                .contextModule(ContextModule(this))
                .networkModule(NetworkModule())
                .databaseModule(DatabaseModule())
                .eventModule(EventModule())
                .build()

        activityComponent = DaggerActivityComponent.builder()
                .appComponent(appComponent)
                .bluetoothModule(BluetoothModule())
                .build()

        activityComponent.inject(this)

        Realm.init(this)

        val realmConfig = RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build()

        Realm.setDefaultConfiguration(realmConfig)

        ratingHelper.incrementAppOpens()
    }
}
