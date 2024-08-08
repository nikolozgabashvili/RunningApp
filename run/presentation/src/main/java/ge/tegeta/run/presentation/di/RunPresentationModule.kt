package ge.tegeta.run.presentation.di

import ge.tegeta.run.domain.RunningTracker
import ge.tegeta.run.presentation.active_run.ActiveRunViewModel
import ge.tegeta.run.presentation.run_overview.RunOverviewViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val runPresentationModule = module {
    singleOf(::RunningTracker)

    viewModelOf(::RunOverviewViewModel)
    viewModelOf(::ActiveRunViewModel)
}