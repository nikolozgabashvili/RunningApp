package ge.tegeta.run.location

import android.content.Context
import android.location.LocationManager
import android.os.Looper
import androidx.core.content.getSystemService
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import ge.tegeta.core.domain.location.LocationWithAltitude
import ge.tegeta.run.domain.LocationObserver
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class AndroidLocationObserver(
    private val context: Context

) : LocationObserver {
    private val client = LocationServices.getFusedLocationProviderClient(context)

    override fun observeLocation(interval: Long): Flow<LocationWithAltitude> {
        return callbackFlow {
            val locationManager = context.getSystemService<LocationManager>()
            var isGpsEnabled = false
            var isNetworkEnabled = false
            while (!isGpsEnabled && !isNetworkEnabled) {
                isGpsEnabled = locationManager?.isProviderEnabled(LocationManager.GPS_PROVIDER) == true
                isNetworkEnabled = locationManager?.isProviderEnabled(LocationManager.NETWORK_PROVIDER) == true
                if (!isGpsEnabled && !isNetworkEnabled) {
                    delay(3000L)
                }

            }
            client.lastLocation.addOnSuccessListener {
                it?.let {location->
                    trySend(location.toLocationWithAltitude())
                }
            }
            val request = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY,interval).build()
            val locationCallBack = object :LocationCallback(){
                override fun onLocationResult(result: LocationResult) {
                    super.onLocationResult(result)
                    result.locations.lastOrNull()?.let {location->
                        trySend(location.toLocationWithAltitude())
                    }
                }
            }
            client.requestLocationUpdates(request,locationCallBack, Looper.getMainLooper())
            awaitClose{
                client.removeLocationUpdates(locationCallBack)
            }
        }
    }
}