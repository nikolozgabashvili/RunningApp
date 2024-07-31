package ge.tegeta.run.presentation.active_run

import ge.tegeta.core.domain.location.Location
import ge.tegeta.run.domain.RunData
import kotlin.time.Duration

data class ActiveRunState(
    val elapsedTime: Duration = Duration.ZERO,
    val shouldTrack: Boolean = false,
    val runData:RunData = RunData(),
    val hasStartedRunning: Boolean = false,
    val currentLocation: Location? = null,
    val isRunFinished:Boolean = false,
    val isSavingRun:Boolean = false,
    val showLocationRationale:Boolean = false,
    val showNotificationRationale:Boolean = false
)