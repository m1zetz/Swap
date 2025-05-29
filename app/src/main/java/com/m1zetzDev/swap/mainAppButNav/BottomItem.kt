package com.m1zetzDev.swap.mainAppButNav

import androidx.compose.ui.graphics.vector.ImageVector
import com.m1zetzDev.swap.R

sealed class BottomItem(val title: String, val iconId: Int, val route: String) {
    object myItems_Home : BottomItem("Home", R.drawable.test_icon, "home")
    object chats : BottomItem("Chats", R.drawable.test_icon, "chats")
    object ribbon : BottomItem("Ribbon", R.drawable.test_icon, "ribbon")
    object exchanges : BottomItem("Exchanges", R.drawable.test_icon, "exchanges")
    object settings : BottomItem("Settings", R.drawable.test_icon, "settings")

}