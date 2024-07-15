package ge.tegeta.core.domain

interface SessionTokenStorage {
    suspend fun get(): AuthInfo?

    suspend fun set(info: AuthInfo?)
}