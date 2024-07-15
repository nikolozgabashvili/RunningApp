package ge.tegeta.trackerapp.di

import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import ge.tegeta.auth.data.EmailPatternValidator
import ge.tegeta.auth.domain.PatternValidator
import ge.tegeta.auth.domain.UserDataValidator
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val appModule = module {
    single<SharedPreferences>{
        EncryptedSharedPreferences(
            androidApplication(),
            "auth",
            MasterKey(androidApplication()),
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM

        )
    }

}