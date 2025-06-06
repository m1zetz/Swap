package com.m1zetzDev.swap.mainAppButNav.MainScreens.BottomNavViewModels

import androidx.compose.material3.Card
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class ExchangesViewModel : ViewModel() {

    var listOfExchanges by mutableStateOf(emptyList<Cards>())


}
data class ExchangeCard(
    val name: String,
    val category: String,
    val description: String,
    val imageUri: String
)
