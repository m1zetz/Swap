package com.m1zetzDev.swap.auth

import androidx.compose.material3.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.m1zetzDev.swap.common.TextField

class SignUpViewModel : ViewModel() {

    var messageEmail by mutableStateOf(TextField())
    var messagePassword by mutableStateOf(TextField())

    fun obtainEvent(regEvent: RegEvent) {
        when (regEvent) {
            is RegEvent.OnChangeEmail -> {
                messageEmail = messageEmail.copy(value = regEvent.email)
            }

            is RegEvent.OnChangePassword -> {
                messagePassword = messagePassword.copy(value = regEvent.password)
            }
        }
    }
}

sealed class RegEvent() {
    class OnChangeEmail(var email: String) : RegEvent()
    class OnChangePassword(var password: String) : RegEvent()
}