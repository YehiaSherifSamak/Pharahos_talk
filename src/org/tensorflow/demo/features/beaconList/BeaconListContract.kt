package org.tensorflow.demo.features.beaconList

import org.tensorflow.demo.models.BeaconSaved
import org.tensorflow.demo.utils.BasePresenter
import io.realm.RealmResults
import org.altbeacon.beacon.Beacon
import org.altbeacon.beacon.BeaconConsumer
import org.altbeacon.beacon.BeaconManager

/**
 * Created by bridou_n on 22/08/2017.
 */

interface BeaconListContract {
    interface View : BeaconConsumer {
        fun showTutorial() : Boolean

        fun showEmptyView(show: Boolean)
        fun setAdapter(beaconResults: RealmResults<BeaconSaved>)

        fun updateBluetoothState(state: BeaconListActivity.BluetoothState, isEnabled: Boolean)
        fun keepScreenOn(status: Boolean)

        fun showBluetoothNotEnabledError()
        fun showScanningState(state: Boolean)

        fun hasCoarseLocationPermission() : Boolean
        fun hasSomePermissionPermanentlyDenied(perms: List<String>) : Boolean
        fun askForCoarseLocationPermission()
        fun showEnablePermissionSnackbar()

        fun showRating(step: Int, show: Boolean = true)
        fun redirectToStorePage()

        fun showClearDialog()
        fun startSettingsActivity()

        fun showLoggingError()
        fun showGenericError(msg: String)
    }

    interface Presenter : BasePresenter {
        fun setBeaconManager(bm: BeaconManager)

        fun onBeaconServiceConnect()
        fun onLocationPermissionGranted()
        fun onLocationPermissionDenied(requestCode: Int, permList: List<String>)

        fun toggleScan()
        fun startScan()
        fun stopScan()

        fun storeBeaconsAround(beacons: Collection<Beacon>)

        fun onBluetoothToggle()
        fun onSettingsClicked()
        fun onClearClicked()
        fun onClearAccepted()

        fun onRatingInteraction(step: Int, answer: Boolean)

        fun clear()
    }
}