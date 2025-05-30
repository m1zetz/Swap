package com.m1zetzDev.swap.mainAppButNav

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.imageResource
import com.m1zetzDev.swap.R

sealed class BottomItem(val title: String, val iconId: Int, val route: String) {
    object myItems_Home : BottomItem("Home", R.drawable.icon_home, "home")
    object chats : BottomItem("Chats", R.drawable.icon_chats, "chats")
    object ribbon : BottomItem("Ribbon", R.drawable.icon_ribbon, "ribbon")
    object exchanges : BottomItem("Exchanges", R.drawable.icon_exchanges, "exchanges")
    object settings : BottomItem("Settings", R.drawable.icon_settings, "settings")

}
