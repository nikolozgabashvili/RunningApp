package ge.tegeta.run.presentation.run_overview

import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ge.tegeta.core.presentation.designsystem.AnalyticsIcon
import ge.tegeta.core.presentation.designsystem.LogoIcon
import ge.tegeta.core.presentation.designsystem.LogoutIcon
import ge.tegeta.core.presentation.designsystem.RunIcon
import ge.tegeta.core.presentation.designsystem.TrackerAppTheme
import ge.tegeta.core.presentation.designsystem.components.FloatingActionButton
import ge.tegeta.core.presentation.designsystem.components.MyScaffold
import ge.tegeta.core.presentation.designsystem.components.Toolbar
import ge.tegeta.core.presentation.designsystem.components.util.DropDownItem
import org.koin.androidx.compose.koinViewModel

@Composable
fun RunOverviewScreenRoot(
    onStartRunClick:() ->Unit,
    viewModel: RunOverviewViewModel = koinViewModel()
) {
    RunOverviewScreenRootScreen(
        onAction = {
            when(it){

                RunOverviewAction.OnStartClick ->onStartRunClick()
                else->Unit
            }
            viewModel.onAction(it)
        }

    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RunOverviewScreenRootScreen(
    onAction: (RunOverviewAction) -> Unit
) {
    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(
        state = topAppBarState
    )

    MyScaffold(
        topAppBar = {
            Toolbar(
                showBackButton = false,
                title = "Runique",
                scrollBehavior = scrollBehavior,
                startContent = {
                    Icon(
                        imageVector = LogoIcon,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(30.dp),
                    )
                },
                menuItems = listOf(

                    DropDownItem(
                        title = "Analytics",
                        icon = AnalyticsIcon
                    ),
                    DropDownItem(
                        title = "logOut",
                        icon = LogoutIcon
                    ),
                ),
                onMenuItemClick = {
                    when (it) {
                        0 -> onAction(RunOverviewAction.OnAnalyticsClick)
                        1 -> onAction(RunOverviewAction.OnLogoutClick)
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                icon = RunIcon,
                onClick = { onAction(RunOverviewAction.OnStartClick) })

        }
    ) { padding ->

    }


}

@Preview
@Composable
private fun RunOverviewScreenRootScreenPreview() {

    TrackerAppTheme {
        RunOverviewScreenRootScreen(
            onAction = {}

        )
    }
}