package com.m1zetzDev.swap.mainAppButNav.MainScreens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.m1zetzDev.swap.R
import com.m1zetzDev.swap.mainAppButNav.MainScreens.BottomNavViewModels.HomeAddItemViewModel
import com.m1zetzDev.swap.ui.theme.backgroundColorPurple1
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
                    title = { Text("All announcements", fontSize = 22.sp) },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = backgroundColorPurple1,
                        titleContentColor = whiteForUi
                    )
                )
            },
            content = { paddingValues ->
                Column(
                    modifier = Modifier
                        .fillMaxSize().padding(paddingValues).padding(vertical = 8.dp),
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
                                modifier = Modifier
                                    .size(width = max(240.dp, 380.dp), height = max(100.dp, 150.dp))
                            ) {
                                Row(
                                    horizontalArrangement = Arrangement.Start,
                                    modifier = Modifier.fillMaxSize()
                                ) {
                                    Image(
                                        painter = painterResource(
                                            R.drawable.icon_camera
                                        ),
                                        contentDescription = "",
                                        Modifier.size(max(100.dp, 140.dp))
                                    )
                                    Column() {
                                        // name
                                        Text(
                                            text = "${cards.name}, Category: ${cards.category}",
                                            modifier = Modifier
                                                .padding(16.dp),
                                            textAlign = TextAlign.Center,
                                        )
                                        //description
                                        Text(
                                            text = cards.description,
                                            modifier = Modifier
                                                .padding(16.dp),
                                            textAlign = TextAlign.Center,
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
