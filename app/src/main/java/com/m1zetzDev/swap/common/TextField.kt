package com.m1zetzDev.swap.common

import kotlinx.serialization.Serializable

data class TextField(
    val value: String = "",
    val errorMessage: String? = null
)