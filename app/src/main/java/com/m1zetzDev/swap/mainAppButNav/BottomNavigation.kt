package com.m1zetzDev.swap.mainAppButNav
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

import com.m1zetzDev.swap.mainAppButNav.MainScreens.Chats
import com.m1zetzDev.swap.mainAppButNav.MainScreens.Exchanges
import com.m1zetzDev.swap.mainAppButNav.MainScreens.HomePage

import com.m1zetzDev.swap.mainAppButNav.MainScreens.Ribbon
import com.m1zetzDev.swap.mainAppButNav.MainScreens.Settings


import com.m1zetzDev.swap.ui.theme.forIcons
import com.m1zetzDev.swap.ui.theme.transparent


@Composable
fun AppBottomNavigation() {


    val listItems = listOf(
        BottomItem.myItems_Home,
        BottomItem.chats,
        BottomItem.ribbon,
        BottomItem.exchanges,
        BottomItem.settings
    )

    var selectedIndex by remember {
        mutableIntStateOf(0)
    }

    var showBottomSheet by remember { mutableStateOf(false) }


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
                        selected = selectedIndex == index,
                        onClick = {
                            if (index == 4) {
                                showBottomSheet = true
                            }
                            else {
                                selectedIndex = index
                                showBottomSheet = false
                            }
                        },
                        icon = {
                            Icon(painterResource(navItem.iconId), contentDescription = "")
                        },
                        label = { navItem.title })
                }
            }
        }) { innerPadding ->
        ContentScreen(modifier = Modifier.padding(innerPadding), selectedIndex)
        if (showBottomSheet) {
            Settings(
                state = true,
                onDismiss = { showBottomSheet = false }
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

