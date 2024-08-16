package ge.tegeta.analytics.data

import ge.tegeta.analytics.domain.AnalyticsRepository
import ge.tegeta.analytics.domain.AnalyticsValues
import ge.tegeta.core.database.dao.AnalyticsDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.toDuration

class RoomAnalyticsRepositoryImpl(
    private val analyticsDao: AnalyticsDao
) :AnalyticsRepository{
    override suspend fun getAnalyticValues(): AnalyticsValues {
        return withContext(Dispatchers.IO){
            val totalDistance = async { analyticsDao.getTotalDistance() }
            val totalTime = async { analyticsDao.getTotalTimeRun() }
            val fastestRun = async { analyticsDao.getMaxRunSpeed() }
            val avgDistance = async { analyticsDao.getAvgDistancePerRun() }
            val avgPace = async { analyticsDao.getAvgPacePerRun() }

            AnalyticsValues(
                totalDistanceRun = totalDistance.await(),
                totalTimeRun = totalTime.await().milliseconds,
                fastestEverRun = fastestRun.await(),
                avgDistancePerRun = avgDistance.await(),
                avgPacePerRun = avgPace.await()
            )

        }

    }
}