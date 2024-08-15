package ge.tegeta.core.presentation.designsystem

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

val DarkColorScheme = darkColorScheme(
    primary = TrackerGreen,
    background = TrackerBlack,
    surface = TrackerDarkGray,
    secondary = TrackerWhite,
    tertiary = TrackerWhite,
    primaryContainer = TrackerGreen30,
    onPrimary = TrackerWhite,
    onBackground = TrackerWhite,
    onSurface = TrackerWhite,
    onSurfaceVariant = TrackerGray,
    error = TrackerDarkRed,
    errorContainer = TrackerDarkRed5

)

@Composable
fun TrackerAppTheme(
    content: @Composable () -> Unit
) {
    val colorScheme = DarkColorScheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
        }
    }

    MaterialTheme(
        colorScheme = DarkColorScheme,
        content = content
    )
}