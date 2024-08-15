package ge.tegeta.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ge.tegeta.core.database.dao.RunDao
import ge.tegeta.core.database.entity.RunEntity

@Database(
    entities = [RunEntity::class],
    version = 1
)
abstract class RunDatabase : RoomDatabase() {
    abstract val runDao: RunDao
}