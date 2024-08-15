package ge.tegeta.core.data.run

import ge.tegeta.core.domain.run.LocalRunDataSource
import ge.tegeta.core.domain.run.RemoteRunDataSource
import ge.tegeta.core.domain.run.Run
import ge.tegeta.core.domain.run.RunId
import ge.tegeta.core.domain.run.RunRepository
import ge.tegeta.core.domain.util.DataError
import ge.tegeta.core.domain.util.EmptyResult
import ge.tegeta.core.domain.util.Result
import ge.tegeta.core.domain.util.asEmptyDataResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow

class OfflineFirstRunRepository(
    private val localRunDataSource: LocalRunDataSource,
    private val remoteRunDataSource: RemoteRunDataSource,
    private val applicationScope: CoroutineScope
) : RunRepository {

    override fun getRuns(): Flow<List<Run>> {
        return localRunDataSource.getRuns()
    }

    override suspend fun fetchRuns(): EmptyResult<DataError> {
        return when (val result = remoteRunDataSource.getRuns()) {
            is Result.Error -> result.asEmptyDataResult()
            is Result.Success -> {
                applicationScope.async {
                    localRunDataSource.upsertRuns(result.data).asEmptyDataResult()
                }.await()
            }
        }
    }

    override suspend fun upsertRun(run: Run, mapPicture: ByteArray): EmptyResult<DataError> {
        val localResult = localRunDataSource.upsertRun(run)
        if (localResult !is Result.Success) {
            return localResult.asEmptyDataResult()
        }
        val runWithId = run.copy(id = localResult.data)
        val remoteResult = remoteRunDataSource.postRun(runWithId, mapPicture)
        return when (remoteResult) {
            is Result.Error -> {
                Result.Success(Unit)
            }

            is Result.Success -> {
                applicationScope.async {

                    localRunDataSource.upsertRun(remoteResult.data).asEmptyDataResult()
                }.await()
            }
        }
    }

    override suspend fun deleteRun(id: RunId) {
        localRunDataSource.deleteRun(id)
        val remoteResult = applicationScope.async {
            remoteRunDataSource.deleteRun(id)
        }.await()
    }
}
