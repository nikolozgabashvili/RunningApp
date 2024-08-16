package ge.tegeta.run.data

import android.content.Context
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.await
import ge.tegeta.core.database.dao.RunPendingSyncDao
import ge.tegeta.core.database.entity.DeletedRunSyncEntity
import ge.tegeta.core.database.entity.RunPendingSyncEntity
import ge.tegeta.core.database.mappers.toRunEntity
import ge.tegeta.core.domain.SessionTokenStorage
import ge.tegeta.core.domain.run.Run
import ge.tegeta.core.domain.run.RunId
import ge.tegeta.core.domain.run.SyncRunScheduler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit
import kotlin.time.Duration
import kotlin.time.toJavaDuration

class SyncRunWorkerScheduler(
    private val context: Context,
    private val pendingSyncDao: RunPendingSyncDao,
    private val sessionTokenStorage: SessionTokenStorage,
    private val applicationScope: CoroutineScope
) : SyncRunScheduler {

    private val workManager = WorkManager.getInstance(context)

    override suspend fun scheduleSync(syncType: SyncRunScheduler.SyncType) {
        when (syncType) {
            is SyncRunScheduler.SyncType.CreateRun -> scheduleCreateRunWorker(
                syncType.run,
                syncType.mapPictureBytes
            )

            is SyncRunScheduler.SyncType.DeleteRun -> scheduleDeleteRunWorker(syncType.runId)
            is SyncRunScheduler.SyncType.FetchRuns -> scheduleFetchRunsWorker(syncType.interval)
        }
    }

    private suspend fun scheduleDeleteRunWorker(runId: RunId) {
        val userId = sessionTokenStorage.get()?.userId ?: return
        val entity = DeletedRunSyncEntity(runId = runId, userId = userId)
        pendingSyncDao.upsertDeletedRunSyncEntity(entity)

        val workRequest = OneTimeWorkRequestBuilder<DeleteRunWorker>()
            .addTag("delete_work")
            .setConstraints(
                Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
            ).setBackoffCriteria(
                backoffPolicy = BackoffPolicy.EXPONENTIAL,
                backoffDelay = 2000L,
                timeUnit = TimeUnit.MILLISECONDS
            ).setInputData(
                Data.Builder().putString(DeleteRunWorker.RUN_ID, entity.runId).build()
            ).build()
        applicationScope.launch {
            workManager.enqueue(workRequest).await()
        }.join()

        applicationScope.launch {
            workManager.enqueue(workRequest).await()
        }.join()
    }

    private suspend fun scheduleCreateRunWorker(run: Run, mapPictureBytes: ByteArray) {
        val userId = sessionTokenStorage.get()?.userId ?: return
        val pendingRun = RunPendingSyncEntity(
            run = run.toRunEntity(),
            mapPictureBytes = mapPictureBytes,
            userId = userId
        )
        pendingSyncDao.upsertRunPendingSyncEntity(pendingRun)


        val workRequest = OneTimeWorkRequestBuilder<CreateRunWorker>()
            .addTag("create_work")
            .setConstraints(
                Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
            ).setBackoffCriteria(
                backoffPolicy = BackoffPolicy.EXPONENTIAL,
                backoffDelay = 2000L,
                timeUnit = TimeUnit.MILLISECONDS
            ).setInputData(
                Data.Builder().putString(CreateRunWorker.RUN_ID, pendingRun.runId).build()
            ).build()
        applicationScope.launch {
            workManager.enqueue(workRequest).await()
        }.join()
    }

    private suspend fun scheduleFetchRunsWorker(interval: Duration) {
        val isSyncScheduled = withContext(Dispatchers.IO) {
            workManager.getWorkInfosByTag("sync_work").get().isNotEmpty()
        }
        if (isSyncScheduled) return
        val workRequest = PeriodicWorkRequestBuilder<FetchRunsWorker>(
            repeatInterval = interval.toJavaDuration()
        ).setConstraints(
            Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
        ).setBackoffCriteria(
            backoffPolicy = BackoffPolicy.EXPONENTIAL,
            backoffDelay = 2000L,
            timeUnit = TimeUnit.MILLISECONDS
        ).setInitialDelay(
            30, TimeUnit.MINUTES
        ).addTag("sync_work").build()

        workManager.enqueue(workRequest).await()

    }

    override suspend fun cancelAllSyncs() {
        WorkManager.getInstance(context).cancelAllWork().await()
    }

}