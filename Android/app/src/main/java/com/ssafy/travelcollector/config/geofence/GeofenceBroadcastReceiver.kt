package com.ssafy.travelcollector.config.geofence

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofenceStatusCodes
import com.google.android.gms.location.GeofencingEvent

private const val TAG = "GeofenceBroadcastReceiv"
class GeofenceBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, p1: Intent?) {
        Log.d(TAG, "onReceive1: herere")
//        Log.d(TAG, "onReceive2: $p1")
        Log.d(TAG, "onReceive3: $p1 \n ${p1?.extras.toString()}")
        val geofencingEvent = GeofencingEvent.fromIntent(p1!!)
        if(geofencingEvent!!.hasError()){
            val error = GeofenceStatusCodes.getStatusCodeString(geofencingEvent.errorCode)
            Log.e(TAG, "onReceive3: $error",)
            return
        }

        val geofenceTransition = geofencingEvent.geofenceTransition

        if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER ||
            geofenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT ||
            geofenceTransition == Geofence.GEOFENCE_TRANSITION_DWELL
            ) {

            val triggeringGeofences = geofencingEvent.triggeringGeofences

            val transitionCallback:(id: String)->Unit = {
                id ->
                when(geofenceTransition){
                    Geofence.GEOFENCE_TRANSITION_ENTER -> GeofenceManager.geofenceCallback.onEnter(id)
                    Geofence.GEOFENCE_TRANSITION_DWELL -> GeofenceManager.geofenceCallback.onDwell(id)
                    Geofence.GEOFENCE_TRANSITION_EXIT -> GeofenceManager.geofenceCallback.onExit(id)
                    else->{}
                }
            }

            triggeringGeofences!!.forEach {
                transitionCallback(it.requestId)
//                Log.d(TAG, "onReceive: abcd ${it.requestId} - $transitionMsg")
//                Toast.makeText(context, "abcd ${it.requestId} - $transitionMsg", Toast.LENGTH_LONG).show()
            }

        } else {
            Toast.makeText(context, "Unknown", Toast.LENGTH_LONG).show()
        }

    }

}