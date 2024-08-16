@file:OptIn(ExperimentalFoundationApi::class, ExperimentalFoundationApi::class)

package ge.tegeta.run.presentation.run_overview

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
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
import ge.tegeta.run.presentation.run_overview.components.RunListItem
import org.koin.androidx.compose.koinViewModel

@Composable
fun RunOverviewScreenRoot(
    onAnalyticsClick: () -> Unit,
    onStartRunClick: () -> Unit,
    onLogoutClick:() -> Unit,
    viewModel: RunOverviewViewModel = koinViewModel()
) {
    RunOverviewScreenRootScreen(
        state = viewModel.state,
        onAction = {
            when (it) {
                RunOverviewAction.OnAnalyticsClick -> onAnalyticsClick()
                RunOverviewAction.OnLogoutClick -> onLogoutClick()
                RunOverviewAction.OnStartClick -> onStartRunClick()
                else -> Unit
            }
            viewModel.onAction(it)
        }

    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RunOverviewScreenRootScreen(
    state: RunOverviewState,
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
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection)
                .padding(horizontal = 16.dp),
            contentPadding = padding,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(
                items = state.runs,
                key = { it.id }
            ) { run ->
                RunListItem(
                    runUi = run,
                    onDeleteClick = { onAction(RunOverviewAction.DeleteRun(run)) },
                    modifier = Modifier.animateItemPlacement()
                )

            }

        }
    }


}

@Preview
@Composable
private fun RunOverviewScreenRootScreenPreview() {

    TrackerAppTheme {
        RunOverviewScreenRootScreen(
            state = RunOverviewState(),
            onAction = {}

        )
    }
}