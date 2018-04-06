package org.tensorflow.demo.features.beaconList

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import butterknife.*
import org.tensorflow.demo.R
import org.tensorflow.demo.models.BeaconSaved
import io.realm.RealmRecyclerViewAdapter
import io.realm.RealmResults

class BeaconsRecyclerViewAdapter(val data: RealmResults<BeaconSaved>,
                                 val ctx: Context,
                                 val onLongClickListener: OnControlsOpen?) :
        RealmRecyclerViewAdapter<BeaconSaved, BeaconsRecyclerViewAdapter.BaseHolder>(data, true) {

    companion object {
        private val TAG = "BEACONS_RV_ADAPTER"
    }
    open class BaseHolder(itemView: View, val ctx: Context, val listener: OnControlsOpen?) : RecyclerView.ViewHolder(itemView) {
        @BindView(R.id.card) lateinit var cardView: CardView

        @BindView(R.id.beacon_type) lateinit var beaconType: TextView
        @BindView(R.id.address) lateinit var address: TextView
        @BindView(R.id.manufacturer) lateinit var manufacturer: TextView

        @BindView(R.id.distance) lateinit var distance: TextView
        @BindView(R.id.last_seen) lateinit var lastSeen: TextView
        @BindView(R.id.roomnumber)lateinit var roomnum:TextView
        @BindView(R.id.rssi) lateinit var rssi: TextView
        @BindView(R.id.tx) lateinit var tx: TextView

        @BindViews(R.id.battery_container, R.id.temperature_container, R.id.uptime_container, R.id.pdu_sent_container) lateinit var tlmData: List<@JvmSuppressWildcards LinearLayout>
        @BindView(R.id.battery) lateinit var battery: TextView

        @BindView(R.id.uptime) lateinit var uptime: TextView
        @BindView(R.id.pdu_sent) lateinit var pduSent: TextView

        // Included layouts
        @BindView(R.id.ibeacon_altbeacon_item) lateinit var iBeaconLayout: View
        protected var beaconDisplayed: BeaconSaved? = null
        val a: Int = 1
        var s: String = a.toString()

        val b: Int = 2
        var c: String = b.toString()


        open fun bindView(beacon: BeaconSaved) {
            beaconDisplayed = beacon

            address.text = ""
            distance.text = ""
            manufacturer.text = ""
            lastSeen.text = ""

            rssi.text = ""
            tx.text = ""

            val telemetry = beacon.telemetryData
            if (telemetry != null) {
                tlmData.forEach { it.visibility = View.VISIBLE }
                battery.text = ""

                uptime.text =  ""
                pduSent.text = ""
            } else {
                tlmData.forEach { it.visibility = View.GONE }
            }
        }

        fun hideAllLayouts() {
            iBeaconLayout.visibility = View.GONE
        }



        @OnLongClick(R.id.card)
        fun onLongClick(): Boolean {
            beaconDisplayed?.let {
                listener?.onOpenControls(it)
            }
            return true
        }
    }

    class IBeaconAltBeaconHolder(itemView: View, ctx: Context, listener: OnControlsOpen?) : BaseHolder(itemView, ctx, listener) {
       @BindView(R.id.proximity_uuid) lateinit var proximityUUID: TextView
        //@BindView(R.id.major) lateinit var major: TextView
       // @BindView(R.id.minor) lateinit var minor: TextView

        init {
            ButterKnife.bind(this, itemView)
        }

        override fun bindView(beacon: BeaconSaved) {
            super.bindView(beacon)

            cardView.setCardBackgroundColor(ContextCompat.getColor(ctx, R.color.ibeaconBackground))
            hideAllLayouts()
            iBeaconLayout.visibility = View.VISIBLE

            beaconType.text = ""

            val ibeaconData = beacon.ibeaconData
            if (ibeaconData != null) {
                proximityUUID.text = ""
               // major.text = ""
               // minor.text = ""

                if(ibeaconData.minor==s){
                    fun Context.toast(message: CharSequence) =
                            Toast.makeText(this, "ROOM 2", Toast.LENGTH_LONG).show()

                    roomnum.text=String.format("This is Room 1")

                }
                else if(ibeaconData.minor==c){
                    fun Context.toast(message: CharSequence) =
                            Toast.makeText(this, "ROOM 2", Toast.LENGTH_LONG).show()

                    roomnum.text=String.format("THIS IS TUTANKHAMON ROOM ")

                }
            }
        }
    }




    override fun getItemCount(): Int {
        return super.getItemCount() + 1
    }

    override fun getItemViewType(position: Int): Int {
        if (position >= data.size) {
            return R.layout.footer_item
        }
        val b = getItem(position)

        when (b?.beaconType) {
            BeaconSaved.TYPE_ALTBEACON, BeaconSaved.TYPE_IBEACON -> return R.layout.ibeacon_altbeacon_item
            else -> return R.layout.ibeacon_altbeacon_item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder {
        // The actual layout file to inflate
        val layout = if (viewType == R.layout.footer_item) viewType else R.layout.beacon_item
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)

        when (viewType) {
            R.layout.ibeacon_altbeacon_item -> return IBeaconAltBeaconHolder(view, ctx, onLongClickListener)
            else -> return BaseHolder(view, ctx, onLongClickListener)
        }
    }

    override fun onBindViewHolder(holder: BaseHolder, position: Int) {
        if (getItemViewType(position) != R.layout.footer_item) {
            val beacon = getItem(position)

            if (beacon != null) {
                holder.bindView(beacon)
            }
        }
    }

    interface OnControlsOpen {
        fun onOpenControls(beacon: BeaconSaved)
    }
}