package ge.tegeta.run.presentation.active_run.maps

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.google.android.gms.maps.model.JointType
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.Polyline
import ge.tegeta.core.domain.location.LocationTimestamp

@Composable
fun PolylinesComposable(locations: List<List<LocationTimestamp>>) {
    val polylines = remember(locations) {
        locations.map {
            it.zipWithNext { time1, time2 ->
                PolyLineUi(
                    location1 = time1.location.location,
                    location2 = time2.location.location,
                    color = PolyLineColorCalculator.locationsToColor(time1, time2)
                )

            }
        }
    }
    polylines.forEach { polyline ->
        polyline.forEach { polyLineUi ->
            Polyline(
                points = listOf(
                    LatLng(polyLineUi.location1.lat, polyLineUi.location1.long),
                    LatLng(polyLineUi.location2.lat, polyLineUi.location2.long)
                ), color = polyLineUi.color,
                jointType = JointType.BEVEL
            )

        }

    }
}