package org.tensorflow.demo.utils.extensionFunctions

import org.tensorflow.demo.models.BeaconSaved
import io.realm.Realm
import io.realm.RealmResults
import io.realm.Sort

fun Realm.getScannedBeacons(blocked: Boolean = false) : RealmResults<BeaconSaved> {
    return this.where(BeaconSaved::class.java)
            .equalTo("isBlocked", blocked)
            .findAllSortedAsync(arrayOf( "distance"), arrayOf( Sort.ASCENDING))
}
/*fun Realm.getScannedBeacons(blocked: Boolean = false) : RealmResults<BeaconSaved> {
    return this.where(BeaconSaved::class.java)
            .equalTo("isBlocked", blocked)
            .findAllSortedAsync(arrayOf("lastMinuteSeen", "distance"), arrayOf(Sort.DESCENDING, Sort.ASCENDING))
    }*/

/*fun Realm.getScannedBeacons(blocked: Boolean = false) : RealmResults<BeaconSaved> {
    this.beginTransaction()
    var myBeacon =  this.where(BeaconSaved::class.java)
            .equalTo("isBlocked", blocked)
            .findAllSortedAsync(arrayOf("distance"), arrayOf( Sort.ASCENDING)).first()
    var myList = this.where(BeaconSaved::class.java).greaterThan("distance", myBeacon!!.getDistace()).findAll()
    myList.deleteAllFromRealm()
    this.commitTransaction()
    return this.where(BeaconSaved::class.java)
            .equalTo("isBlocked", blocked)
            .findAllSortedAsync(arrayOf("lastMinuteSeen", "distance"), arrayOf(Sort.DESCENDING, Sort.ASCENDING))
}*/
fun Realm.getBeaconsScannedAfter(timestamp: Long) : RealmResults<BeaconSaved> {
    return this.where(BeaconSaved::class.java)
            .greaterThan("lastSeen", timestamp)
            .equalTo("isBlocked", false)
            .findAllSortedAsync(arrayOf( "distance"), arrayOf( Sort.ASCENDING))//I add mabeejn koseen
}

fun Realm.getBeaconWithId(hashcode: Int) : BeaconSaved? {
    return this.where(BeaconSaved::class.java).equalTo("hashcode", hashcode).findFirst()
}

fun Realm.clearScannedBeacons(blocked: Boolean = false) {
    this.executeTransactionAsync { tRealm ->
        tRealm.where(BeaconSaved::class.java)
                .equalTo("isBlocked", blocked)
                .findAll().deleteAllFromRealm()
    }
}

fun Realm.flushDb() {
    this.executeTransactionAsync { tRealm ->
        tRealm.deleteAll()
    }
}