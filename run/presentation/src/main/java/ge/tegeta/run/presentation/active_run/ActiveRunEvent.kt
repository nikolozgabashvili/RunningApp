package ge.tegeta.run.presentation.active_run

sealed interface ActiveRunEvent {
    data class  Error(val message: String) : ActiveRunEvent
    data object RunSaved : ActiveRunEvent

}