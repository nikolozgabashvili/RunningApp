@file:OptIn(ExperimentalFoundationApi::class)

package ge.tegeta.auth.presentation.register

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.text2.input.textAsFlow
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ge.tegeta.auth.domain.AuthRepository
import ge.tegeta.auth.domain.UserDataValidator
import ge.tegeta.auth.presentation.R
import ge.tegeta.core.domain.util.DataError
import ge.tegeta.core.domain.util.Result
import ge.tegeta.core.presentation.ui.UiText
import ge.tegeta.core.presentation.ui.asUiText
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch


class RegisterViewModel(
    private val userDataValidator: UserDataValidator,
    private val repository: AuthRepository
) : ViewModel() {
    var state by mutableStateOf(RegisterState())
        private set

    private val eventChannel = Channel<RegisterEvent>()

    val events = eventChannel.receiveAsFlow()

    init {
        state.email.textAsFlow()
            .onEach { email ->
                val isValid = userDataValidator.isValidEmail(email.toString())
                state = state.copy(
                    isEmailValid = isValid,
                    canRegister = isValid && state.passwordValidationState.isValidPassword
                            && !state.isRegistering
                )

            }.launchIn(viewModelScope)

        state.password.textAsFlow()
            .onEach { password ->
                val passwordValidationState =
                    userDataValidator.validatePassword(password.toString())
                state = state.copy(
                    passwordValidationState = passwordValidationState,
                    canRegister = state.isEmailValid && passwordValidationState.isValidPassword
                            && !state.isRegistering
                )

            }.launchIn(viewModelScope)
    }

    fun onAction(action: RegisterAction) {
        when (action) {
            RegisterAction.OnRegisterClick -> register()
            RegisterAction.OnTogglePasswordVisibilityCheck -> state =
                state.copy(isPasswordVisible = !state.isPasswordVisible)

            else -> Unit
        }

    }

    private fun register() {
        state = state.copy(isRegistering = true)
        viewModelScope.launch {

            val result = repository.register(
                email = state.email.text.toString().trim(),
                password = state.password.text.toString()
            )
            state = state.copy(isRegistering = false)

            when (result) {

                is Result.Error -> {
                    if (result.error == DataError.Network.CONFLICT) {
                        eventChannel.send(RegisterEvent.Error(UiText.StringResource(R.string.error_email_already_exists)))
                    } else
                        eventChannel.send(RegisterEvent.Error(result.error.asUiText()))
                }

                is Result.Success -> eventChannel.send(RegisterEvent.RegistrationSuccess)
            }
        }
    }
}