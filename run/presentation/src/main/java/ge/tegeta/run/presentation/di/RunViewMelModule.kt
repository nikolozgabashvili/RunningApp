package ge.tegeta.run.presentation.di

import ge.tegeta.run.presentation.active_run.ActiveRunViewModel
import ge.tegeta.run.presentation.run_overview.RunOverviewViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val runOverviewViewModelModule = module {
    viewModelOf(::RunOverviewViewModel)
    viewModelOf(::ActiveRunViewModel)
}