package com.m1zetzDev.swap.mainAppButNav
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding

import androidx.compose.material3.Icon

import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Scaffold

import androidx.compose.runtime.Composable

import androidx.compose.runtime.mutableIntStateOf


import androidx.compose.ui.Modifier

import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.m1zetzDev.swap.mainAppButNav.MainScreens.BottomNavViewModels.SettingsViewModel
import com.m1zetzDev.swap.mainAppButNav.MainScreens.BottomNavigationViewModel

import com.m1zetzDev.swap.mainAppButNav.MainScreens.Chats
import com.m1zetzDev.swap.mainAppButNav.MainScreens.Exchanges
import com.m1zetzDev.swap.mainAppButNav.MainScreens.HomePage

import com.m1zetzDev.swap.mainAppButNav.MainScreens.Ribbon
import com.m1zetzDev.swap.mainAppButNav.MainScreens.Settings


import com.m1zetzDev.swap.ui.theme.forIcons
import com.m1zetzDev.swap.ui.theme.transparent


@Composable
fun AppBottomNavigation(signOut: () -> Unit) {

    val listItems = listOf(
        BottomItem.myItems_Home,
        BottomItem.chats,
        BottomItem.ribbon,
        BottomItem.exchanges,
        BottomItem.settings
    )


    val vmBottomNavigation: BottomNavigationViewModel = viewModel()

    val vmSettings: SettingsViewModel = viewModel()

    Scaffold(modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar() {
                listItems.forEachIndexed { index, navItem ->
                    NavigationBarItem(
                        colors = NavigationBarItemColors(
                            selectedIconColor = forIcons,
                            unselectedIconColor = forIcons,
                            disabledIconColor = forIcons,
                            selectedTextColor = forIcons,
                            selectedIndicatorColor = transparent,
                            unselectedTextColor = forIcons,
                            disabledTextColor = forIcons
                        ),
                        selected = vmBottomNavigation.currentScreenIndex == index,
                        onClick = {
                            if (index == 4) {
                                vmSettings.showBottomSheet = true
                            }
                            else {
                                vmBottomNavigation.currentScreenIndex = index
                                vmSettings.showBottomSheet = false
                            }
                        },
                        icon = {
                            Icon(painterResource(navItem.iconId), contentDescription = "")
                        },
                        label = { navItem.title })
                }
            }
        }) { innerPadding ->
        ContentScreen(modifier = Modifier.padding(innerPadding), vmBottomNavigation.currentScreenIndex)
        if (vmSettings.showBottomSheet) {
            Log.d("", "попал в условие")
            Settings(
                viewModel = vmSettings,
                signOut = {signOut()},
                onDismiss = { vmSettings.showBottomSheet = false}
            )
        }
    }

}


@Composable
fun ContentScreen(modifier: Modifier = Modifier, selectedIndex: Int) {
    when (selectedIndex) {
        0 -> HomePage()
        1 -> Chats()
        2 -> Ribbon()
        3 -> Exchanges()
    }
}

