package ge.tegeta.core.data.auth

import android.content.SharedPreferences
import ge.tegeta.core.domain.AuthInfo
import ge.tegeta.core.domain.SessionTokenStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class EncryptedSessionStorage(
    private val sharedPreferences: SharedPreferences

) : SessionTokenStorage {
    override suspend fun get(): AuthInfo? {
        return withContext(Dispatchers.IO) {
            val json = sharedPreferences.getString(KEY_AUTH_INFO, null)
            json?.let {
                Json.decodeFromString<AuthInfoSerializable>(it).toAuthInfo()
            }

        }

    }

    override suspend fun set(info: AuthInfo?) {
        withContext(Dispatchers.IO) {
            if (info == null) {
                sharedPreferences.edit().remove(KEY_AUTH_INFO).apply()
                return@withContext
            }
            val json = Json.encodeToString(info.toAuthInfoSerializable())
            sharedPreferences.edit().putString(KEY_AUTH_INFO, json).apply()

        }
    }

    companion object {
        private const val KEY_AUTH_INFO = "auth_info"
    }
}