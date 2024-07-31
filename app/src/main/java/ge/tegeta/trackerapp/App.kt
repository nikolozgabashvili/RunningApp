package ge.tegeta.trackerapp

import android.app.Application
import ge.tegeta.auth.data.di.authDataModule
import ge.tegeta.auth.presentation.di.authViewModelModule
import ge.tegeta.core.data.di.coreDataModule
import ge.tegeta.run.presentation.di.runOverviewViewModelModule
import ge.tegeta.trackerapp.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(
                authDataModule,
                authViewModelModule,
                appModule,
                coreDataModule,
                runOverviewViewModelModule
            )
        }
    }
}