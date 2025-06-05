package com.m1zetzDev.swap.mainAppButNav.MainScreens

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import android.graphics.BitmapFactory

import androidx.compose.material.icons.filled.Refresh
import android.util.Base64
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.m1zetzDev.swap.R
import com.m1zetzDev.swap.mainAppButNav.MainScreens.BottomNavViewModels.HomeAddItemViewModel
import com.m1zetzDev.swap.ui.theme.backgroundColorPurple1
import com.m1zetzDev.swap.ui.theme.backgroundColorPurple3
import com.m1zetzDev.swap.ui.theme.whiteForUi

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Ribbon() {
    val vmAddItem: HomeAddItemViewModel = viewModel()
    LaunchedEffect(Unit) {
        vmAddItem.getData()
    }
    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("All Announcements", fontSize = 22.sp) },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = backgroundColorPurple1,
                        titleContentColor = whiteForUi
                    ),
                    actions = {
                        IconButton(onClick = {vmAddItem.getData()}) {
                            Icon(Icons.Filled.Refresh, contentDescription = "")
                        }
                    }
                )
            },
            content = { paddingValues ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(vertical = 8.dp),
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        // ТУТ КАРТОЧКИ
                        items(vmAddItem.listOfCards) { cards ->
                            Card(
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                                ),
                                modifier = Modifier.fillMaxWidth().padding(10.dp).height(height = 140.dp)
                            ) {
                                Row(
                                    horizontalArrangement = Arrangement.Start,
                                    modifier = Modifier.fillMaxSize()
                                ) {
                                    val base64image = Base64.decode(cards.imageUri, Base64.DEFAULT)
                                    val bitmap = BitmapFactory.decodeByteArray(
                                        base64image,
                                        0,
                                        base64image.size
                                    )
                                    Column(verticalArrangement = Arrangement.Center,
                                        horizontalAlignment = Alignment.CenterHorizontally) {
                                        if (bitmap != null) {
                                            AsyncImage(
                                                model = bitmap,
                                                contentDescription = "",
                                                modifier = Modifier.size(140.dp).padding(all = 10.dp).clip(shape = RoundedCornerShape(10.dp)),
                                                contentScale = ContentScale.Crop,

                                                )
                                        } else {
                                            Image(
                                                painter = painterResource(id = R.drawable.icon_camera),
                                                null,
                                                modifier = Modifier.size(140.dp).padding(10.dp)
                                            )
                                        }
                                    }

                                    Column(verticalArrangement = Arrangement.Top,
                                        horizontalAlignment = Alignment.Start) {
                                        // name
                                        Text(
                                            text = cards.name.replaceFirstChar { it.uppercaseChar() },
                                            modifier = Modifier,
                                            color = backgroundColorPurple1,
                                            fontSize = 27.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                        //category
                                        Text(
                                            text = "Category: ${cards.category}",
                                            modifier = Modifier,
                                            color = backgroundColorPurple3,
                                            fontSize = 18.sp,
                                            fontWeight = FontWeight.Bold
                                        )

                                        //description
                                        Text(
                                            text = cards.description.replaceFirstChar { it.uppercaseChar() },
                                            modifier = Modifier,
                                            color = backgroundColorPurple3,
                                            fontSize = 15.sp
                                        )

                                    }
                                }


                            }
                            Spacer(modifier = Modifier.size((8.dp)))
                        }
                    }
                }
            })


    }
}
