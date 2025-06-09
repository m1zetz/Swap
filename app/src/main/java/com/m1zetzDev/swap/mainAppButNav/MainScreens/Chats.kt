package com.m1zetzDev.swap.mainAppButNav.MainScreens

import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.m1zetzDev.swap.R
import com.m1zetzDev.swap.common.TextField
import com.m1zetzDev.swap.mainAppButNav.MainScreens.BottomNavViewModels.ChatsViewModel
import com.m1zetzDev.swap.ui.theme.backgroundColorPurple1
import com.m1zetzDev.swap.ui.theme.lightBlue
import com.m1zetzDev.swap.ui.theme.whiteForUi


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Chats(
    navController: NavController,
    vmChats: ChatsViewModel
) {

    vmChats.updateCurrentId()
    LaunchedEffect(Unit) {
        vmChats.getAllMySuccessfulExchanges()
    }



    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(stringResource(R.string.chats), fontSize = 22.sp) },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = backgroundColorPurple1,
                        titleContentColor = whiteForUi
                    )

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

                        items(vmChats.listOfCardsUsers) { cards ->

                            val currentId = Firebase.auth.currentUser?.uid
                            val otherUserId = if (cards.acceptedId == currentId) {
                                cards.userIdOther
                            } else {
                                cards.acceptedId
                            }

                            val imageBase64 = if (cards.acceptedId == currentId) {
                                cards.imageUriOther
                            } else {
                                cards.acceptedUri
                            }




                            Card(
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp)
                                    .height(height = 100.dp)
                                    .clickable {
                                        vmChats.getMessages(vmChats.currentId!!, otherUserId)
                                        vmChats.selectUserForChat(otherUserId, imageBase64)
                                        navController.navigate("chat_screen")
                                    }
                            ) {
                                Row(
                                    horizontalArrangement = Arrangement.Start,
                                    modifier = Modifier.fillMaxSize()
                                ) {
                                    val base64image = Base64.decode(imageBase64, Base64.DEFAULT)
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
                                                    .size(100.dp)
                                                    .padding(all = 10.dp)
                                                    .clip(shape = RoundedCornerShape(10.dp)),
                                                contentScale = ContentScale.Crop,

                                                )
                                        } else {
                                            Image(
                                                painter = painterResource(id = R.drawable.icon_camera),
                                                null,
                                                modifier = Modifier
                                                    .size(100.dp)
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
                                            text = otherUserId.replaceFirstChar { it.uppercaseChar() },
                                            modifier = Modifier,
                                            color = backgroundColorPurple1,
                                            fontSize = 27.sp,
                                            fontWeight = FontWeight.Bold
                                        )

                                    }
                                }


                            }
                            Spacer(modifier = Modifier.size((8.dp)))

                        }
                    }
                }
            }
        )
    }
}

@Composable
fun ChatScreen(
    messageTextField: TextField,
    onChangeMessage: (message: String) -> Unit,
    vmChats: ChatsViewModel
) {
    val navBarHeight = WindowInsets.navigationBars.getBottom(LocalDensity.current)
    val messages = vmChats.messagesList

    val listState = rememberLazyListState()

    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.lastIndex)
        }
    }
    if (vmChats.otherId.value.isBlank()) {
        return
    }

    Column(Modifier.background(lightBlue)) {

        //ебучая карточка
        cardChat(vmChats.otherUri.value, vmChats.otherId.value)


        Box(Modifier.fillMaxSize()) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxHeight(0.8f)
                    .fillMaxWidth(),
                state = listState
            ) {
                items(vmChats.messagesList) { messages ->
                    val isMyMessage = messages.senderId == vmChats.currentId

                    Row(horizontalArrangement = if (isMyMessage) Arrangement.End else Arrangement.Start,
                        modifier = Modifier.padding(8.dp).fillMaxWidth()) {
                        Card(
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier
                                .wrapContentWidth()
                                .widthIn(max = 280.dp)) {

                            Text(messages.text,
                                modifier = Modifier.padding(8.dp),
                                fontSize = 20.sp,
                                color = backgroundColorPurple1)
                        }
                    }

                }
            }
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.BottomEnd
            ) {

                Row {
                    //ВВОД СООБЩЕНИЯ
                    TextField(
                        value = messageTextField.value,
                        onValueChange = {onChangeMessage(it)},
                        modifier = Modifier
                            .weight(1f)
                            .padding(bottom = IntPixelsToDp(navBarHeight)),
                        shape = RectangleShape)
                    Button(onClick = {vmChats.sendMessage(vmChats.currentId.toString(), vmChats.otherId.value)
                    }) {
                        Image(Icons.Default.PlayArrow, contentDescription = "", Modifier.size(56.dp))
                    }
                }
            }

        }
    }

}

@Composable
fun IntPixelsToDp(px: Int): Dp {
    val density = LocalDensity.current
    return with(density) { px.toDp() }
}

@Composable
fun cardChat(imageBase64:String, otherUserId: String){
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        shape = RectangleShape,
        modifier = Modifier
            .fillMaxWidth()
            .height(height = 100.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.fillMaxSize()
        ) {
            val base64image = Base64.decode(imageBase64, Base64.DEFAULT)
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
                            .size(100.dp)
                            .padding(all = 10.dp)
                            .clip(shape = RoundedCornerShape(10.dp)),
                        contentScale = ContentScale.Crop,

                        )
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.icon_camera),
                        null,
                        modifier = Modifier
                            .size(100.dp)
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
                    text = otherUserId,
                    modifier = Modifier,
                    color = backgroundColorPurple1,
                    fontSize = 27.sp,
                    fontWeight = FontWeight.Bold
                )

            }
        }


    }
}