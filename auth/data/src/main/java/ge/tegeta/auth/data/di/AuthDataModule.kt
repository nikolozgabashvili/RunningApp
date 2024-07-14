package ge.tegeta.auth.data.di

import ge.tegeta.auth.data.AuthRepositoryImpl
import ge.tegeta.auth.data.EmailPatternValidator
import ge.tegeta.auth.domain.AuthRepository
import ge.tegeta.auth.domain.PatternValidator
import ge.tegeta.auth.domain.UserDataValidator
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val authDataModule = module {
    single<PatternValidator> {
        EmailPatternValidator
    }
    singleOf(::UserDataValidator)
    singleOf(::AuthRepositoryImpl).bind<AuthRepository>()

}