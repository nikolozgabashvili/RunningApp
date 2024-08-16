package ge.tegeta.trackerapp

import android.app.Application
import ge.tegeta.auth.data.di.authDataModule
import ge.tegeta.auth.presentation.di.authViewModelModule
import ge.tegeta.core.data.di.coreDataModule
import ge.tegeta.core.database.di.databaseModule
import ge.tegeta.run.data.di.runDataModule
import ge.tegeta.run.location.di.locationModule
import ge.tegeta.run.network.di.networkModule
import ge.tegeta.run.presentation.di.runPresentationModule
import ge.tegeta.trackerapp.di.appModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.workmanager.koin.workManagerFactory
import org.koin.core.context.startKoin
import timber.log.Timber

class App : Application() {
    val applicationScope = CoroutineScope(SupervisorJob())
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        startKoin {
            androidLogger()
            androidContext(this@App)
            workManagerFactory()
            modules(
                authDataModule,
                authViewModelModule,
                appModule,
                coreDataModule,
                runPresentationModule,
                locationModule,
                databaseModule,
                networkModule,
                runDataModule
            )
        }
    }
}