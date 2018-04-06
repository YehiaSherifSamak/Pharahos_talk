package org.tensorflow.demo.dagger.components

import org.tensorflow.demo.AppSingleton
import  org.tensorflow.demo.dagger.PerActivity
import  org.tensorflow.demo.dagger.modules.BluetoothModule
import  org.tensorflow.demo.features.beaconList.BeaconListActivity
import  org.tensorflow.demo.features.beaconList.ControlsBottomSheetDialog
import   org.tensorflow.demo.features.settings.SettingsActivity

import dagger.Component
import org.altbeacon.beacon.BeaconManager

/**
 * Created by bridou_n on 08/10/2016.
 */
@PerActivity
@Component(
        dependencies = arrayOf(AppComponent::class),
        modules = arrayOf(BluetoothModule::class)
)
interface ActivityComponent {
    fun providesBeaconManager() : BeaconManager


    fun inject(app: AppSingleton)
    fun inject(activity: BeaconListActivity)
    fun inject(activity: SettingsActivity)

    fun inject(bs: ControlsBottomSheetDialog)
}
