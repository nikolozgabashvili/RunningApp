package ge.tegeta.analytics.data.di

import ge.tegeta.analytics.data.RoomAnalyticsRepositoryImpl
import ge.tegeta.analytics.domain.AnalyticsRepository
import ge.tegeta.core.database.RunDatabase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val analyticsDataModule = module{
    singleOf(::RoomAnalyticsRepositoryImpl).bind<AnalyticsRepository>()
    single{
        get<RunDatabase>().analyticsDao
    }

}