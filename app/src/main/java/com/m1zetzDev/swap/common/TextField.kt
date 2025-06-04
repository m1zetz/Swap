package com.m1zetzDev.swap.common

import kotlinx.serialization.Serializable

data class TextField(
    var value: String = "",
    val errorMessage: String? = null
)