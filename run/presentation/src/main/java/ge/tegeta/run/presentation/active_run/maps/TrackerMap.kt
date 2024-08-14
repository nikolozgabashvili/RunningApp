package ge.tegeta.run.presentation.active_run.maps

import android.graphics.Bitmap
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.MarkerComposable
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import ge.tegeta.core.domain.location.Location
import ge.tegeta.core.domain.location.LocationTimestamp
import ge.tegeta.core.presentation.designsystem.RunIcon
import ge.tegeta.run.presentation.R

@Composable
fun TrackerMap(
    isRunFinished: Boolean,
    currentLocation: Location?,
    locations: List<List<LocationTimestamp>>,
    onSnapshot: (Bitmap) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val mapStyle = remember { MapStyleOptions.loadRawResourceStyle(context, R.raw.map_style) }
    val cameraPosition = rememberCameraPositionState()
    val markerState = rememberMarkerState()

    val markerPositionLat by animateFloatAsState(
        targetValue = currentLocation?.lat?.toFloat() ?: 0f,
        label = "",
        animationSpec = tween(durationMillis = 500)
    )

    val markerPositionLong by animateFloatAsState(
        targetValue = currentLocation?.long?.toFloat() ?: 0f,
        label = "",
        animationSpec = tween(durationMillis = 500)
    )

    val markerPosition = remember(markerPositionLat, markerPositionLong) {
        LatLng(markerPositionLat.toDouble(), markerPositionLong.toDouble())

    }
    LaunchedEffect(markerPosition, isRunFinished) {
        if (!isRunFinished) {
            markerState.position = markerPosition

        }

    }
    LaunchedEffect(currentLocation,isRunFinished) {
        if (currentLocation!=null && !isRunFinished) {
            val latLong = LatLng(currentLocation.lat, currentLocation.long)
            cameraPosition.animate(
               CameraUpdateFactory.newLatLngZoom(latLong, 17f)
            )
        }

    }
    GoogleMap(
        cameraPositionState = cameraPosition,
        properties = MapProperties(mapStyleOptions = mapStyle),
        uiSettings = MapUiSettings(
            zoomControlsEnabled = false
        )
    ){
        PolylinesComposable(locations = locations)
        if (!isRunFinished && currentLocation!=null) {
            MarkerComposable(currentLocation, state = markerState) {
               Box(modifier = Modifier
                   .size(35.dp)
                   .clip(CircleShape)
                   .background(MaterialTheme.colorScheme.primary), contentAlignment = Alignment.Center) {
                   Icon(imageVector = RunIcon, contentDescription = null, tint = MaterialTheme.colorScheme.onPrimary, modifier = Modifier.size(20.dp))
               }

            }
        }
    }


}