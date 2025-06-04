package com.m1zetzDev.swap.mainAppButNav.MainScreens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class BottomNavigationViewModel : ViewModel() {
    var currentScreenIndex by mutableStateOf(0)
}