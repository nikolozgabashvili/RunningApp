package ge.tegeta.run.location

import android.location.Location
import ge.tegeta.core.domain.location.LocationWithAltitude

fun Location.toLocationWithAltitude(): LocationWithAltitude {
    return LocationWithAltitude(
       location = ge.tegeta.core.domain.location.Location(
           lat = latitude,
           long = longitude
       ),
        altitude = altitude
    )
}