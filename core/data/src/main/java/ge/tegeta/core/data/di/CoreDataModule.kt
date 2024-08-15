package ge.tegeta.core.data.di

import ge.tegeta.core.data.auth.EncryptedSessionStorage
import ge.tegeta.core.data.networking.HttpClientFactory
import ge.tegeta.core.data.run.OfflineFirstRunRepository
import ge.tegeta.core.domain.SessionTokenStorage
import ge.tegeta.core.domain.run.RunRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val coreDataModule = module {
    single {
        HttpClientFactory(get()).build()
    }
    singleOf(::EncryptedSessionStorage).bind<SessionTokenStorage>()

    singleOf(::OfflineFirstRunRepository).bind<RunRepository>()

}