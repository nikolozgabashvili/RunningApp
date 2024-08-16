package ge.tegeta.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import ge.tegeta.core.database.entity.DeletedRunSyncEntity
import ge.tegeta.core.database.entity.RunPendingSyncEntity

@Dao
interface RunPendingSyncDao {
    //Pending runs

    @Query("SELECT * FROM RunPendingSyncEntity WHERE userId = :userId")
    suspend fun getAllRunPendingSyncEntities(userId:String): List<RunPendingSyncEntity>

    @Query("SELECT * FROM RunPendingSyncEntity WHERE runId = :runId")
    suspend fun getAllRunPendingSyncEntity(runId:String): RunPendingSyncEntity?

    @Upsert
    suspend fun upsertRunPendingSyncEntity(entity: RunPendingSyncEntity)

    @Query("DELETE FROM RunPendingSyncEntity WHERE runId = :runId")
    suspend fun deleteRunPendingSyncEntity(runId:String)

    //Deleted runs
    @Query("SELECT * FROM DeletedRunSyncEntity WHERE userId = :userId")
    suspend fun getAllDeletedRunSyncEntities(userId:String): List<DeletedRunSyncEntity>

    @Upsert
    suspend fun upsertDeletedRunSyncEntity(entity: DeletedRunSyncEntity)

    @Query("DELETE FROM DeletedRunSyncEntity WHERE runId = :runId")
    suspend fun deleteDeletedRunSyncEntity(runId:String)



}