package ge.tegeta.run.domain

import ge.tegeta.core.domain.location.Location
import ge.tegeta.core.domain.location.LocationTimestamp
import kotlin.time.Duration

data class RunData(
    val distanceMeters: Int = 0,
    val pace: Duration = Duration.ZERO,
    val locations:List<List<LocationTimestamp>> = emptyList()
)
