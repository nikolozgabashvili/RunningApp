package ge.tegeta.core.domain.run

import ge.tegeta.core.domain.util.DataError
import ge.tegeta.core.domain.util.EmptyResult
import kotlinx.coroutines.flow.Flow
import javax.xml.crypto.Data

interface RunRepository {
    fun getRuns(): Flow<List<Run>>

    suspend fun fetchRuns(): EmptyResult<DataError>

    suspend fun upsertRun(run: Run,mapPicture:ByteArray):EmptyResult<DataError>

    suspend fun deleteRun(id: RunId)


}