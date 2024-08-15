package ge.tegeta.run.presentation.run_overview

import ge.tegeta.run.presentation.run_overview.model.RunUi

data class RunOverviewState(
    val runs: List<RunUi> = emptyList()
)
