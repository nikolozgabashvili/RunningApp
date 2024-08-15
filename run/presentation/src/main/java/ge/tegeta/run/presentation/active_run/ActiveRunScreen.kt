@file:OptIn(ExperimentalMaterial3Api::class)

package ge.tegeta.run.presentation.active_run

import android.Manifest
import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ge.tegeta.core.presentation.designsystem.StartIcon
import ge.tegeta.core.presentation.designsystem.StopIcon
import ge.tegeta.core.presentation.designsystem.TrackerAppTheme
import ge.tegeta.core.presentation.designsystem.components.CustomDialog
import ge.tegeta.core.presentation.designsystem.components.FloatingActionButton
import ge.tegeta.core.presentation.designsystem.components.MyScaffold
import ge.tegeta.core.presentation.designsystem.components.RuniqueActionButton
import ge.tegeta.core.presentation.designsystem.components.RuniqueOutlinedActionButton
import ge.tegeta.core.presentation.designsystem.components.Toolbar
import ge.tegeta.run.presentation.R
import ge.tegeta.run.presentation.active_run.components.RunDataCard
import ge.tegeta.run.presentation.active_run.maps.TrackerMap
import ge.tegeta.run.presentation.active_run.service.ActiveRunService
import ge.tegeta.run.presentation.util.hasLocationPermission
import ge.tegeta.run.presentation.util.hasNotificationPermission
import ge.tegeta.run.presentation.util.shouldShowLocationPermissionRationale
import ge.tegeta.run.presentation.util.shouldShowNotificationPermissionRationale
import org.koin.androidx.compose.koinViewModel
import java.io.ByteArrayOutputStream

@Composable

fun ActiveRunScreenRoot(
    onServiceToggle: (isServiceRunning: Boolean) -> Unit,
    viewModel: ActiveRunViewModel = koinViewModel()

) {

    ActiveRunScreen(
        state = viewModel.state,
        onAction = viewModel::onAction,
        onServiceToggle = onServiceToggle

    )

}

@Composable
private fun ActiveRunScreen(
    state: ActiveRunState,
    onServiceToggle: (isServiceRunning: Boolean) -> Unit,
    onAction: (ActiveRunAction) -> Unit

) {
    val context = LocalContext.current
    val permissionManager = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    )
    { perms ->
        val hasCourseLocationPermission = perms[Manifest.permission.ACCESS_COARSE_LOCATION] == true
        val hasFineLocationPermission = perms[Manifest.permission.ACCESS_FINE_LOCATION] == true
        val hasNotificationPermission = if (Build.VERSION.SDK_INT >= 33)
            perms[Manifest.permission.POST_NOTIFICATIONS] == true
        else true

        val activity = context as ComponentActivity
        val showLocationRationale = activity.shouldShowLocationPermissionRationale()
        val showNotificationRationale = activity.shouldShowNotificationPermissionRationale()

        onAction(
            ActiveRunAction.SubmitLocationPermissionInfo(
                acceptedLocationPermission = hasCourseLocationPermission && hasFineLocationPermission,
                showLocationRationale = showLocationRationale,
            )
        )
        onAction(
            ActiveRunAction.SubmitNotificationPermissionInfo(
                acceptedNotificationPermission = hasNotificationPermission,
                showNotificationRationale = showNotificationRationale,
            )
        )


    }

    LaunchedEffect(true) {
        val activity = context as ComponentActivity
        val shouldShowNotificationRational = activity.shouldShowNotificationPermissionRationale()
        val shouldShowLocationRational = activity.shouldShowLocationPermissionRationale()

        onAction(
            ActiveRunAction.SubmitLocationPermissionInfo(
                acceptedLocationPermission = context.hasLocationPermission(),
                showLocationRationale = shouldShowLocationRational,
            )
        )
        onAction(
            ActiveRunAction.SubmitNotificationPermissionInfo(
                acceptedNotificationPermission = context.hasNotificationPermission(),
                showNotificationRationale = shouldShowNotificationRational,
            )
        )
        if (!shouldShowLocationRational && !shouldShowNotificationRational) {
            permissionManager.requestPermissions(context)
        }
    }
    LaunchedEffect(state.isRunFinished) {
        if (state.isRunFinished) {
            onServiceToggle(false)
        }

    }

    LaunchedEffect(state.shouldTrack) {
        if (context.hasLocationPermission() && state.shouldTrack && !ActiveRunService.isServiceActive) {
            onServiceToggle(true)
        }

    }

    MyScaffold(
        withGradient = false,
        topAppBar = {
            Toolbar(
                showBackButton = true,
                title = "Active Run",
                onBackClick = { onAction(ActiveRunAction.OnBackClick) },
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                icon = if (state.shouldTrack) StopIcon else StartIcon,
                iconSize = 20.dp,
                onClick = { onAction(ActiveRunAction.OnToggleRunClick) })
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
        ) {
            TrackerMap(
                isRunFinished = state.isRunFinished,
                currentLocation = state.currentLocation,
                locations = state.runData.locations,
                onSnapshot = { bmp ->
                    val stream = ByteArrayOutputStream()
                    stream.use {
                        bmp.compress(
                            Bitmap.CompressFormat.JPEG,
                            80,
                            it
                        )
                    }
                    onAction(ActiveRunAction.OnRunProcessed(stream.toByteArray()))
                }
            )
            RunDataCard(
                modifier = Modifier
                    .padding(16.dp)
                    .padding(it),
                elapsedTime = state.elapsedTime, runData = state.runData
            )
        }

    }
    if (!state.shouldTrack && state.hasStartedRunning) {
        CustomDialog(
            title = stringResource(id = R.string.running_poused),
            onDismiss = { onAction(ActiveRunAction.OnResumeRunClick) },
            description = "resume or finish run",
            primaryButton = {
                RuniqueActionButton(
                    modifier = Modifier.weight(1f),
                    text = "resume",
                    isLoading = false
                ) {
                    onAction(ActiveRunAction.OnResumeRunClick)
                }
            }, secondaryButton = {
                RuniqueOutlinedActionButton(
                    modifier = Modifier.weight(1f),
                    text = "finish",
                    isLoading = state.isSavingRun
                ) {
                    onAction(ActiveRunAction.OnFinishRunClick)
                }
            }
        )

    }

    if (state.showLocationRationale || state.showNotificationRationale) {
        CustomDialog(
            title = "Permission Required",
            onDismiss = { /*re request permissions*/ },
            description = when {
                state.showLocationRationale && state.showNotificationRationale -> "App needs permission to track your run and send notifications"
                state.showNotificationRationale -> "Notification permission is required to track your run"
                else -> "location permission is required to track your run"
            },
            primaryButton = {
                RuniqueOutlinedActionButton(text = "Ok", isLoading = false) {
                    onAction(ActiveRunAction.DismissDialog)
                    permissionManager.requestPermissions(context)


                }
            })
    }

}

private fun ActivityResultLauncher<Array<String>>.requestPermissions(context: Context) {
    val hasLocationPermission = context.hasLocationPermission()
    val hasNotificationPermission = context.hasNotificationPermission()

    val locationPermissions = arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    )
    val notificationPermission = if (Build.VERSION.SDK_INT >= 33)
        arrayOf(Manifest.permission.POST_NOTIFICATIONS)
    else emptyArray()

    when {
        !hasLocationPermission && !hasNotificationPermission -> launch(locationPermissions + notificationPermission)
        !hasLocationPermission -> launch(locationPermissions)
        !hasNotificationPermission -> launch(notificationPermission)
    }

}

@Preview
@Composable
private fun ActiveRunScreenPreview() {

    TrackerAppTheme {
        ActiveRunScreen(

            state = ActiveRunState(),
            onServiceToggle = {},
            onAction = {}

        )

    }

}