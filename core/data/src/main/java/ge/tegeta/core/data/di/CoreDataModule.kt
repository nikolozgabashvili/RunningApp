package ge.tegeta.core.data.di

import ge.tegeta.core.data.networking.HttpClientFactory
import org.koin.dsl.module

val coreDataModule = module {
    single {
        HttpClientFactory().build()
    }

}