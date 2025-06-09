package com.m1zetzDev.swap.mainAppButNav.MainScreens

import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.m1zetzDev.swap.R
import com.m1zetzDev.swap.mainAppButNav.MainScreens.BottomNavViewModels.ExchangesViewModel
import com.m1zetzDev.swap.ui.theme.backgroundColorPurple1
import com.m1zetzDev.swap.ui.theme.backgroundColorPurple3
import com.m1zetzDev.swap.ui.theme.lightBlue
import com.m1zetzDev.swap.ui.theme.lightGreen
import com.m1zetzDev.swap.ui.theme.lightRed
import com.m1zetzDev.swap.ui.theme.whiteForUi

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Exchanges() {

    val vmExchanges: ExchangesViewModel = viewModel()
    vmExchanges.getMyData()

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(stringResource(R.string.exchanges), fontSize = 22.sp) },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = backgroundColorPurple1,
                        titleContentColor = whiteForUi
                    )
                )
            },
            content = { paddingValues ->
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(1f)
                        .padding(paddingValues),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {

//             ТУТ КАРТОЧКИ
                    items (vmExchanges.listOfCards) { exchange ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp)
                                .height(height = 450.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = lightBlue,

                                )
                        ) {
                            Column() {
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
                                            Base64.decode(exchange.acceptedUri, Base64.DEFAULT)
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
                                                        .padding(10.dp)
                                                        .clip(RoundedCornerShape(10.dp)),
                                                    contentScale = ContentScale.Crop
                                                )
                                            } else {
                                                Image(
                                                    painter = painterResource(id = R.drawable.icon_camera),
                                                    contentDescription = null,
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
                                            Text(
                                                text = exchange.acceptedName.replaceFirstChar { it.uppercaseChar() },
                                                color = backgroundColorPurple1,
                                                fontSize = 27.sp,
                                                fontWeight = FontWeight.Bold
                                            )
                                            Text(
                                                text = "${stringResource(R.string.category)}: ${exchange.acceptedCategory}",
                                                color = backgroundColorPurple3,
                                                fontSize = 18.sp,
                                                fontWeight = FontWeight.Bold
                                            )
                                            Text(
                                                text = exchange.acceptedDescription.replaceFirstChar { it.uppercaseChar() },
                                                color = backgroundColorPurple3,
                                                fontSize = 15.sp
                                            )

                                        }
                                    }
                                }


                                Spacer(modifier = Modifier.size(5.dp))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceEvenly
                                ) {
                                    Image(
                                        painter = painterResource(R.drawable.arrow1),
                                        contentDescription = "",
                                        Modifier.size(70.dp),
                                        colorFilter = ColorFilter.tint(whiteForUi)
                                    )
                                    Spacer(modifier = Modifier.size(20.dp))
                                    Image(
                                        painter = painterResource(R.drawable.arrow2),
                                        contentDescription = "",
                                        Modifier.size(70.dp),
                                        colorFilter = ColorFilter.tint(whiteForUi)
                                    )
                                }


                                Spacer(modifier = Modifier.size(5.dp))



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
                                            Base64.decode(exchange.imageUriOther, Base64.DEFAULT)
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
                                                        .padding(10.dp)
                                                        .clip(RoundedCornerShape(10.dp)),
                                                    contentScale = ContentScale.Crop
                                                )
                                            } else {
                                                Image(
                                                    painter = painterResource(id = R.drawable.icon_camera),
                                                    contentDescription = null,
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
                                            Text(
                                                text = exchange.nameOther.replaceFirstChar { it.uppercaseChar() },
                                                color = backgroundColorPurple1,
                                                fontSize = 27.sp,
                                                fontWeight = FontWeight.Bold
                                            )
                                            Text(
                                                text = "${stringResource(R.string.category)}: ${exchange.categoryOther}",
                                                color = backgroundColorPurple3,
                                                fontSize = 18.sp,
                                                fontWeight = FontWeight.Bold
                                            )
                                            Text(
                                                text = exchange.descriptionOther.replaceFirstChar { it.uppercaseChar() },
                                                color = backgroundColorPurple3,
                                                fontSize = 15.sp
                                            )
                                        }
                                    }
                                }
                                Spacer(modifier = Modifier.size(5.dp))
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                                    Button(
                                        onClick = {vmExchanges.acceptExchange(exchange)}, colors = ButtonColors(
                                            containerColor = lightGreen,
                                            contentColor = whiteForUi,
                                            disabledContainerColor = lightGreen,
                                            disabledContentColor = whiteForUi
                                        ),
                                        modifier = Modifier.padding(5.dp)
                                    ) {
                                        Text(stringResource(R.string.accept), textAlign = TextAlign.Center)
                                    }
                                    Button(
                                        onClick = {}, colors = ButtonColors(
                                            containerColor = lightRed,
                                            contentColor = whiteForUi,
                                            disabledContainerColor = lightRed,
                                            disabledContentColor = whiteForUi
                                        ),
                                        modifier = Modifier.padding(5.dp)
                                    ) {
                                        Text(stringResource(R.string.reject), textAlign = TextAlign.Center)
                                    }
                                }
                            }
                        }
                    }


                }
            })
    }
}
