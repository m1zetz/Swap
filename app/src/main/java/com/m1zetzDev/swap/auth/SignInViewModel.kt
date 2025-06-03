package com.m1zetzDev.swap.auth
import androidx.compose.material3.TextField
import com.m1zetzDev.swap.common.TextField as CustomTextField


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.m1zetzDev.swap.common.TextField
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class AuthViewModel(
    //authRepository: AuthRepository,
) : ViewModel() {

    protected val _viewState: MutableStateFlow<AuthState> = MutableStateFlow(AuthState())
    val viewState = _viewState.asStateFlow()

    var messageEmail by mutableStateOf(TextField())
    var messagePassword by mutableStateOf(TextField())

    fun obtainEvent(authEvent: AuthEvent){
        when(authEvent){
            is AuthEvent.OnChangeEmail -> {
                messageEmail = messageEmail.copy(
                    value = authEvent.email
                )
            }
            is AuthEvent.OnChangePassword -> {
                messagePassword = messagePassword.copy(
                    value = authEvent.password
                )
            }
        }
    }

}

data class AuthState(val a: String = "24"){

}

sealed class AuthEvent{
    class OnChangeEmail(val email: String) : AuthEvent()
    class OnChangePassword(val password: String) : AuthEvent()

}

