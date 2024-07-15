package ge.tegeta.core.data.networking

import android.security.keystore.KeyExpiredException
import kotlinx.serialization.Serializable

@Serializable
data class AccessTokenResponse(
    val accessToken:String,
    val expirationTimeStamp: String
)
