package ge.tegeta.core.domain.run

import ge.tegeta.core.domain.util.DataError
import ge.tegeta.core.domain.util.EmptyResult
import ge.tegeta.core.domain.util.Result

interface RemoteRunDataSource {
    suspend fun getRuns(): Result<List<Run>, DataError.Network>

    suspend fun postRun(run: Run,mapPicture:ByteArray): Result<Run, DataError.Network>

    suspend fun deleteRun(id: RunId): EmptyResult<DataError.Network>
}