package com.ssafy.travelcollector.config.geofence

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.location.LocationServices
import com.ssafy.travelcollector.config.ApplicationClass

private const val TAG = "GeofenceManager"
object GeofenceManager {
    val geofencingClient: GeofencingClient by lazy{
        LocationServices.getGeofencingClient(ApplicationClass.applicationContext())
    }

    private val geofencePendingIntent: PendingIntent by lazy{
        val intent = Intent(ApplicationClass.applicationContext(), GeofenceBroadcastReceiver::class.java)
        PendingIntent.getBroadcast(
            ApplicationClass.applicationContext(),
            100,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE )
    }

    private fun getGeofencingRequest(list: List<Geofence>): GeofencingRequest {
        return GeofencingRequest.Builder().apply{
            setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
            addGeofences(list)
        }.build()
    }

    @SuppressLint("MissingPermission")
    fun addGeofences(lst: List<Geofence>){
        geofencingClient.addGeofences(getGeofencingRequest(lst), geofencePendingIntent).run{
            addOnSuccessListener {
                Log.d(TAG, "addGeofences: $it")
                Toast.makeText(ApplicationClass.applicationContext(), "add success", Toast.LENGTH_SHORT).show()
            }
            addOnFailureListener {
                Toast.makeText(ApplicationClass.applicationContext(), "add fail", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun removeGeofences(){
        geofencingClient.removeGeofences(geofencePendingIntent).run{
            addOnSuccessListener {  }
        }
    }

    interface GeofenceCallback{
        fun onEnter(id: String)
        fun onDwell(id: String)
        fun onExit(id: String)
    }

    lateinit var geofenceCallback: GeofenceCallback

}