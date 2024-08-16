package ge.tegeta.analytics.presentation

sealed interface AnalyticsAction {
    data object OnBackClick : AnalyticsAction
}