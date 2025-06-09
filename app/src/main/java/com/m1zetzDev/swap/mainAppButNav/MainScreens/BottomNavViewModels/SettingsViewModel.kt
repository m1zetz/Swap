package com.m1zetzDev.swap.mainAppButNav.MainScreens.BottomNavViewModels

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController

class SettingsViewModel() : ViewModel() {

    var showBottomSheet by mutableStateOf(false)

    var checkedLightTheme by mutableStateOf(true)

    var checkedDarkTheme by mutableStateOf(false)
}