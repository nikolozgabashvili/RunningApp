@file:OptIn(ExperimentalCoroutinesApi::class)

package ge.tegeta.run.domain

import ge.tegeta.core.domain.location.LocationWithAltitude
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

class RunningTracker(
    private val locationObserver: LocationObserver,
    private val applicationScope: CoroutineScope
) {

    private val isObservingLocation = MutableStateFlow(false)
    val currentLocation = MutableStateFlow<LocationWithAltitude?>(null)

    init {
        applicationScope.launch {
            isObservingLocation
                .collect { isObservingLocation ->
                    if (isObservingLocation) {
                        locationObserver.observeLocation(1000L).collect {
                            currentLocation.value = it
                        }
                    } else {
                        flowOf(null)
                    }
                }

        }
    }

    fun startObservingLocation() {
        isObservingLocation.value = true
    }

    fun stopObservingLocation() {
        isObservingLocation.value = false
    }
}