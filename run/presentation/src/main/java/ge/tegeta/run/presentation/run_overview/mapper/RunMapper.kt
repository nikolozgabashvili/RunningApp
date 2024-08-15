package ge.tegeta.run.presentation.run_overview.mapper

import ge.tegeta.core.domain.run.Run
import ge.tegeta.core.presentation.ui.toFormattedKm
import ge.tegeta.core.presentation.ui.toFormattedKmh
import ge.tegeta.core.presentation.ui.toFormattedMeters
import ge.tegeta.core.presentation.ui.toFormattedPace
import ge.tegeta.run.presentation.run_overview.model.RunUi
import java.time.ZoneId
import java.time.format.DateTimeFormatter


fun Run.toRunUi():RunUi{
    val dateTimeInLocal = dateTimeUtc
        .withZoneSameInstant(ZoneId.systemDefault())
    val formattedDateTime = DateTimeFormatter
        .ofPattern("dd.MM.yyyy HH:mm")
        .format(dateTimeInLocal)
    val distanceKm = distanceMeters/1000.0
    return RunUi(
        id = id!!,
        duration = duration.toString(),
        dateTime = formattedDateTime,
        distance = distanceKm.toFormattedKm(),
        avgSpeed = avgSpeedKmh.toFormattedKmh(),
        maxSpeed = maxSpeedKmh.toFormattedKmh(),
        pace = duration.toFormattedPace(distanceKm),
        totalElevation = totalElevationMeters.toFormattedMeters(),
        mapPictureURL = mapPictureUrl
    )
}