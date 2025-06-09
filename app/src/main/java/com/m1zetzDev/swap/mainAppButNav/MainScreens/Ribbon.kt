package com.m1zetzDev.swap.mainAppButNav.MainScreens

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import android.graphics.BitmapFactory
import android.os.Message

import androidx.compose.material.icons.filled.Refresh
import android.util.Base64
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.animateFloatAsState

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.rememberDismissState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.m1zetzDev.swap.R
import com.m1zetzDev.swap.common.TextField
import com.m1zetzDev.swap.mainAppButNav.MainScreens.BottomNavViewModels.Cards
import com.m1zetzDev.swap.mainAppButNav.MainScreens.BottomNavViewModels.HomeAddItemViewModel
import com.m1zetzDev.swap.mainAppButNav.MainScreens.BottomNavViewModels.RibbonViewModel
import com.m1zetzDev.swap.ui.theme.backgroundColorPurple1
import com.m1zetzDev.swap.ui.theme.backgroundColorPurple3
import com.m1zetzDev.swap.ui.theme.blue
import com.m1zetzDev.swap.ui.theme.bluePink
import com.m1zetzDev.swap.ui.theme.green
import com.m1zetzDev.swap.ui.theme.red
import com.m1zetzDev.swap.ui.theme.whiteForUi


@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun Ribbon() {
    val vmRibbon: RibbonViewModel = viewModel()
    LaunchedEffect(Unit) {
        vmRibbon.getData()
    }
    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(stringResource(R.string.all_announcements), fontSize = 22.sp) },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = backgroundColorPurple1,
                        titleContentColor = whiteForUi
                    ),
                    actions = {
                        IconButton(onClick = { vmRibbon.getData() }) {
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
                        items(vmRibbon.listOfCards) { cards ->
                            val dismissState =
                                rememberDismissState(
                                    confirmStateChange = { dismissValue ->
                                        if (dismissValue == DismissValue.DismissedToStart) {
                                            vmRibbon.nameOther = cards.name
                                            vmRibbon.descriptionOther = cards.description
                                            vmRibbon.categoryOther = cards.category

                                            vmRibbon.uidOther = cards.user_id
                                            vmRibbon.uriOther = cards.imageUri



                                            vmRibbon.removeCard(cards)
                                            vmRibbon.getData()

                                            vmRibbon.stateOfBotttomSheet = true

                                            true
                                        } else {
                                            false
                                        }
                                    }
                                )

                            SwipeToDismiss(
                                state = dismissState,
                                directions = setOf(
                                    DismissDirection.EndToStart,
                                ),
                                background = {
                                    val color by animateColorAsState(
                                        targetValue = when (dismissState.targetValue) {
                                            DismissValue.Default -> whiteForUi
                                            DismissValue.DismissedToStart -> blue
                                            else -> whiteForUi
                                        }
                                    )
                                    val icon = Icons.Default.Add

                                    val scale by animateFloatAsState(
                                        targetValue = if (dismissState.targetValue == DismissValue.Default) 0.8f else 2f
                                    )


                                    Box(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .background(color = color)
                                            .padding(start = 12.dp, end = 12.dp),
                                        contentAlignment = Alignment.CenterEnd
                                    ) {
                                        Icon(
                                            icon,
                                            contentDescription = "",
                                            modifier = Modifier.scale(scale),
                                            tint = whiteForUi
                                        )
                                    }
                                },
                                dismissContent = {
                                    Card(
                                        colors = CardDefaults.cardColors(
                                            containerColor = MaterialTheme.colorScheme.surfaceVariant,
                                        ),
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(10.dp)
                                            .height(height = 140.dp)
                                    ) {
                                        Row(
                                            horizontalArrangement = Arrangement.Start,
                                            modifier = Modifier.fillMaxSize()
                                        ) {
                                            val base64image =
                                                Base64.decode(cards.imageUri, Base64.DEFAULT)
                                            val bitmap = BitmapFactory.decodeByteArray(
                                                base64image,
                                                0,
                                                base64image.size
                                            )
                                            Column(
                                                verticalArrangement = Arrangement.Center,
                                                horizontalAlignment = Alignment.CenterHorizontally
                                            ) {
                                                if (bitmap != null) {
                                                    AsyncImage(
                                                        model = bitmap,
                                                        contentDescription = "",
                                                        modifier = Modifier
                                                            .size(140.dp)
                                                            .padding(all = 10.dp)
                                                            .clip(shape = RoundedCornerShape(10.dp)),
                                                        contentScale = ContentScale.Crop,

                                                        )
                                                } else {
                                                    Image(
                                                        painter = painterResource(id = R.drawable.icon_camera),
                                                        null,
                                                        modifier = Modifier
                                                            .size(140.dp)
                                                            .padding(10.dp)
                                                    )
                                                }
                                            }

                                            Column(
                                                verticalArrangement = Arrangement.Top,
                                                horizontalAlignment = Alignment.Start,
                                                modifier = Modifier.padding(top = 10.dp)
                                            ) {
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
                                                    text = "${stringResource(R.string.category)}: ${cards.category}",
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
                            )

                        }
                    }
                }
            }
        )
    }
    if (vmRibbon.stateOfBotttomSheet) {
        ChooseAd(
            state = true,
            onDismiss = {vmRibbon.stateOfBotttomSheet = false},
            choose = { selectedCard ->
                if(selectedCard.name != "" ){
                    vmRibbon.stateOfBotttomSheet = false
                    vmRibbon.sendData(
                        selectedCard.name,
                        selectedCard.description,
                        selectedCard.category,
                        selectedCard.imageUri

                    )

                }

            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChooseAd(
    state: Boolean,
    onDismiss: () -> Unit,
    choose: (Cards) -> Unit
) {
    val vmRibbon: RibbonViewModel = viewModel()

    val sheetState = rememberModalBottomSheetState()

    if (!state) return
    LaunchedEffect(Unit) {
        vmRibbon.getMyData()
    }

    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = {
            onDismiss()
        },
        modifier = Modifier.fillMaxHeight()

    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
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
                items(vmRibbon.listOfMyCards) { cards ->
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant,
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                            .height(height = 140.dp)
                            .clickable{choose(cards)}
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.Start,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            val base64image =
                                Base64.decode(cards.imageUri, Base64.DEFAULT)
                            val bitmap = BitmapFactory.decodeByteArray(
                                base64image,
                                0,
                                base64image.size
                            )
                            Column(
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                if (bitmap != null) {
                                    AsyncImage(
                                        model = bitmap,
                                        contentDescription = "",
                                        modifier = Modifier
                                            .size(140.dp)
                                            .padding(all = 10.dp)
                                            .clip(shape = RoundedCornerShape(10.dp)),
                                        contentScale = ContentScale.Crop,

                                        )
                                } else {
                                    Image(
                                        painter = painterResource(id = R.drawable.icon_camera),
                                        null,
                                        modifier = Modifier
                                            .size(140.dp)
                                            .padding(10.dp)
                                    )
                                }
                            }

                            Column(
                                verticalArrangement = Arrangement.Top,
                                horizontalAlignment = Alignment.Start,
                                modifier = Modifier.padding(top = 10.dp)
                            ) {
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
                                    text = "${stringResource(R.string.category)}: ${cards.category}",
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
    }
}