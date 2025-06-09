package com.m1zetzDev.swap.mainAppButNav

import android.util.Log

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding

import androidx.compose.material3.Icon

import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Scaffold

import androidx.compose.runtime.Composable


import androidx.compose.ui.Modifier

import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.m1zetzDev.swap.mainAppButNav.MainScreens.BottomNavViewModels.ChatsViewModel
import com.m1zetzDev.swap.mainAppButNav.MainScreens.BottomNavViewModels.HomeAddItemViewModel
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
fun AppBottomNavigation(
    signOut: () -> Unit,
    navController: NavController,
    vmChats: ChatsViewModel,
    vmAddItem: HomeAddItemViewModel
) {

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
                            } else {
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
        ContentScreen(
            modifier = Modifier.padding(innerPadding),
            vmBottomNavigation.currentScreenIndex,
            navController = navController,
            vmChats = vmChats,
            vmAddItem = vmAddItem
        )
        if (vmSettings.showBottomSheet) {
            Settings(
                viewModel = vmSettings,
                signOut = { signOut() },
                onDismiss = { vmSettings.showBottomSheet = false },
                navController = navController,
                toThemes = {navController.navigate("themes")}
            )
        }
    }

}


@Composable
fun ContentScreen(
    modifier: Modifier = Modifier,
    selectedIndex: Int,
    navController: NavController,
    vmChats: ChatsViewModel,
    vmAddItem: HomeAddItemViewModel
) {
    when (selectedIndex) {
        0 -> HomePage(vmAddItem)
        1 -> Chats(navController = navController, vmChats)
        2 -> Ribbon()
        3 -> Exchanges()
    }
}

