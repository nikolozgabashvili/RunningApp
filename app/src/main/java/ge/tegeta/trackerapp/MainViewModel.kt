package ge.tegeta.trackerapp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ge.tegeta.core.domain.SessionTokenStorage
import kotlinx.coroutines.launch

class MainViewModel(
    private val sessionTokenStorage: SessionTokenStorage
) : ViewModel() {

    var state by mutableStateOf(MainState())
        private set

    init {
        viewModelScope.launch {
            state = state.copy(isCheckingAuth = true)
            state = state.copy(isLoggedIn = sessionTokenStorage.get() != null)
            state = state.copy(isCheckingAuth = false)

        }
    }

    fun setAnalyticsDialogVisibility(isVisible: Boolean) {
        state = state.copy(showAnalyticsDialog = isVisible)
    }



}