package ge.tegeta.auth.data

import ge.tegeta.auth.domain.AuthRepository
import ge.tegeta.core.data.networking.post
import ge.tegeta.core.domain.AuthInfo
import ge.tegeta.core.domain.SessionTokenStorage
import ge.tegeta.core.domain.util.DataError
import ge.tegeta.core.domain.util.EmptyResult
import ge.tegeta.core.domain.util.Result
import ge.tegeta.core.domain.util.asEmptyDataResult
import io.ktor.client.HttpClient

class AuthRepositoryImpl(
    private val httpClient: HttpClient,
    private val sessionStorage: SessionTokenStorage
) : AuthRepository {
    override suspend fun register(email: String, password: String): EmptyResult<DataError.Network> {
        return httpClient.post<RegisterRequest, Unit>(
            route = "/register",
            body = RegisterRequest(
                email = email,
                password = password
            )
        )
    }

    override suspend fun login(email: String, password: String): EmptyResult<DataError.Network> {
        val result = httpClient.post<LoginRequest, LoginResponse>(
            route = "/login",
            body = LoginRequest(
                email = email,
                password = password
            )
        )
        if (result is Result.Success){
            sessionStorage.set(
                AuthInfo(
                    accessToken = result.data.accessToken,
                    refreshToken = result.data.refreshToken,
                    userId = result.data.userId
                )
            )
        }
        return result.asEmptyDataResult()

    }

}