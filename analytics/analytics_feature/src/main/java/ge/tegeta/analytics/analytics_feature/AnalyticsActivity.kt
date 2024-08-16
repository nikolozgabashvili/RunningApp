package ge.tegeta.analytics.analytics_feature

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.android.play.core.splitcompat.SplitCompat
import ge.tegeta.analytics.data.di.analyticsDataModule
import ge.tegeta.analytics.presentation.AnalyticsDashboardScreenRoot
import ge.tegeta.analytics.presentation.di.analyticsPresentationModule
import ge.tegeta.core.presentation.designsystem.TrackerAppTheme
import org.koin.core.context.loadKoinModules

class AnalyticsActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadKoinModules(
            listOf(
                analyticsDataModule,
                analyticsPresentationModule
            )
        )
        SplitCompat.installActivity(this)

        setContent {
            TrackerAppTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "analytics_dashboard") {
                    composable("analytics_dashboard") {
                        AnalyticsDashboardScreenRoot(onBackClick = { finish() })
                    }

                }
            }

        }
    }
}