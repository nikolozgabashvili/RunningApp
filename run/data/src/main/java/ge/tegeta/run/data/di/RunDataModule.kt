package ge.tegeta.run.data.di

import ge.tegeta.core.domain.run.SyncRunScheduler
import ge.tegeta.run.data.CreateRunWorker
import ge.tegeta.run.data.FetchRunsWorker
import ge.tegeta.run.data.DeleteRunWorker
import ge.tegeta.run.data.SyncRunWorkerScheduler
import org.koin.androidx.workmanager.dsl.workerOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val runDataModule = module {
    workerOf(::CreateRunWorker)
    workerOf(::FetchRunsWorker)
    workerOf(::DeleteRunWorker)
    singleOf(::SyncRunWorkerScheduler).bind<SyncRunScheduler>()

}