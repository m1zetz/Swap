package com.m1zetzDev.swap.auth

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class AuthViewModel(
    //authRepository: AuthRepository,
) : ViewModel() {

    protected val _viewState: MutableStateFlow<AuthState> = MutableStateFlow(AuthState())
    val viewState = _viewState.asStateFlow()
}

data class AuthState(val a: String = "24"){

}