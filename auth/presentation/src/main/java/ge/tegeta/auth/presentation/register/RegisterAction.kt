package ge.tegeta.auth.presentation.register

sealed interface RegisterAction {
    data object OnTogglePasswordVisibilityCheck : RegisterAction
    data object OnLoginInClick : RegisterAction
    data object OnRegisterClick : RegisterAction
}