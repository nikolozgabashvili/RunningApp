package ge.tegeta.run.presentation.active_run

import ge.tegeta.core.presentation.ui.UiText

sealed interface ActiveRunEvent {
    data class  Error(val message: UiText) : ActiveRunEvent
    data object RunSaved : ActiveRunEvent

}