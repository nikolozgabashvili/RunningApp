package ge.tegeta.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ge.tegeta.core.database.dao.AnalyticsDao
import ge.tegeta.core.database.dao.RunDao
import ge.tegeta.core.database.dao.RunPendingSyncDao
import ge.tegeta.core.database.entity.DeletedRunSyncEntity
import ge.tegeta.core.database.entity.RunEntity
import ge.tegeta.core.database.entity.RunPendingSyncEntity

@Database(
    entities = [RunEntity::class,
        RunPendingSyncEntity::class,
        DeletedRunSyncEntity::class],
    version = 1
)
abstract class RunDatabase : RoomDatabase() {
    abstract val runDao: RunDao
    abstract val runPendingSyncDao: RunPendingSyncDao
    abstract val analyticsDao: AnalyticsDao

}