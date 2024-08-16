package ge.tegeta.analytics.domain

interface AnalyticsRepository {
    suspend fun getAnalyticValues(): AnalyticsValues
}