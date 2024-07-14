package ge.tegeta.trackerapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import ge.tegeta.auth.presentation.intro.IntroScreen
import ge.tegeta.core.presentation.designsystem.TrackerAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TrackerAppTheme {
                IntroScreen(
                    onAction = {

                    }
                )

            }
        }
    }
}
