package ge.tegeta.auth.domain

import ge.tegeta.core.domain.util.DataError
import ge.tegeta.core.domain.util.EmptyResult

interface AuthRepository {
    suspend fun register(email: String, password: String): EmptyResult<DataError.Network>
}