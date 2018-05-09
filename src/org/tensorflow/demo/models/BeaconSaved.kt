package org.tensorflow.demo.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.Ignore
import io.realm.annotations.PrimaryKey
import org.altbeacon.beacon.Beacon
import java.util.*


open class BeaconSaved() : RealmObject(), Parcelable {

    @PrimaryKey
    @SerializedName("hashcode") var hashcode: Int = 0 // hashcode()
    @SerializedName("beaconType") var beaconType: String = "" // Eddystone, altBeacon, iBeacon
    @SerializedName("beaconAddress") var beaconAddress: String? = null // MAC address of the bluetooth emitter
    @SerializedName("manufacturer") var manufacturer: Int = 0
    @SerializedName("txPower") var txPower: Int = 0
    @SerializedName("rssi") var rssi: Int = 0
   public @SerializedName("distance") var distance: Double = 0.toDouble()
    @SerializedName("lastSeen") var lastSeen: Long = 0
    @SerializedName("lastMinuteSeen") var lastMinuteSeen: Long = 0

    /**
     * Specialized field for every beacon type
     */
   public @SerializedName("ibeaconData") var ibeaconData: IbeaconData? = null

    @SerializedName("telemetryData") var telemetryData: TelemetryData? = null

    /**
     * Fields for the app logic
     */
    var isBlocked: Boolean = false

    constructor(parcel: Parcel) : this() {
        hashcode = parcel.readInt()
        beaconType = parcel.readString()
        beaconAddress = parcel.readString()
        manufacturer = parcel.readInt()
        txPower = parcel.readInt()
        rssi = parcel.readInt()
        distance = parcel.readDouble()
        lastSeen = parcel.readLong()
        lastMinuteSeen = parcel.readLong()
    }

    constructor(beacon: Beacon) : this() {
        // Common fields to every beacons
        hashcode = beacon.hashCode()
        lastSeen = Date().time
        lastMinuteSeen = Date().time / 1000 / 60
        beaconAddress = beacon.bluetoothAddress
        manufacturer = beacon.manufacturer
        rssi = beacon.rssi
        txPower = beacon.txPower
        distance = beacon.distance

        if (beacon.serviceUuid == 0xFEAA) { // This is an Eddystone format

            // Do we have telemetry data?
            if (beacon.extraDataFields.size >= 5) {
                telemetryData = TelemetryData(beacon.extraDataFields[0],
                        beacon.extraDataFields[1],
                        TelemetryData.getTemperatureFromTlmField(beacon.extraDataFields[2].toFloat()),
                        beacon.extraDataFields[3],
                        beacon.extraDataFields[4])
            }


        } else { // This is an iBeacon or ALTBeacon
            beaconType = if (beacon.beaconTypeCode == 0xBEAC) BeaconSaved.TYPE_ALTBEACON else BeaconSaved.TYPE_IBEACON // 0x4c000215 is iBeacon
            ibeaconData = IbeaconData(beacon.id1.toString(), beacon.id2.toString(), beacon.id3.toString())
        }
    }

    fun clone() : BeaconSaved {
        val ret = BeaconSaved()

        ret.hashcode = hashcode
        ret.beaconType = beaconType
        ret.beaconAddress = beaconAddress
        ret.manufacturer = manufacturer
        ret.txPower = txPower
        ret.rssi = rssi
        ret.distance = distance
        ret.lastSeen = lastSeen
        ret.lastMinuteSeen = lastMinuteSeen

        ret.ibeaconData = ibeaconData?.clone()

        ret.telemetryData = telemetryData?.clone()

        ret.isBlocked = isBlocked

        return ret
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(hashcode)
        parcel.writeString(beaconType)
        parcel.writeString(beaconAddress)
        parcel.writeInt(manufacturer)
        parcel.writeInt(txPower)
        parcel.writeInt(rssi)
        parcel.writeDouble(distance)
        parcel.writeLong(lastSeen)
        parcel.writeLong(lastMinuteSeen)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BeaconSaved> {

        @Ignore const val TYPE_ALTBEACON = "altbeacon"
        @Ignore const val TYPE_IBEACON = "ibeacon"

        override fun createFromParcel(parcel: Parcel): BeaconSaved {
            return BeaconSaved(parcel)
        }

        override fun newArray(size: Int): Array<BeaconSaved?> {
            return arrayOfNulls(size)
        }
    }

    override fun toString(): String {
        return GsonBuilder().setPrettyPrinting().create().toJson(this.clone())
    }
    public  fun getDistace():Double
    {
        return this.distance
    }

}
