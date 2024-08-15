package ge.tegeta.core.database.di

import androidx.room.Room
import ge.tegeta.core.database.RoomLocalRunDataSource
import ge.tegeta.core.database.RunDatabase
import ge.tegeta.core.domain.run.LocalRunDataSource
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val databaseModule = module{
    single{
        Room.databaseBuilder(
            androidApplication(),
            RunDatabase::class.java,
            "run.db"
        ).build()
    }
    single{
        get<RunDatabase>().runDao
    }
    single{
        get<RunDatabase>().runPendingSyncDao
    }
    singleOf(::RoomLocalRunDataSource).bind<LocalRunDataSource>()



}