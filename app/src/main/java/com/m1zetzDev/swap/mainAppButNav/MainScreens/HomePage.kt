package com.m1zetzDev.swap.mainAppButNav.MainScreens

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.m1zetzDev.swap.R
import com.m1zetzDev.swap.common.TextField
import com.m1zetzDev.swap.mainAppButNav.MainScreens.BottomNavViewModels.Cards
import com.m1zetzDev.swap.mainAppButNav.MainScreens.BottomNavViewModels.FieldsState
import com.m1zetzDev.swap.mainAppButNav.MainScreens.BottomNavViewModels.HomeAddItemViewModel
import com.m1zetzDev.swap.ui.theme.backgroundColorPurple1
import com.m1zetzDev.swap.ui.theme.green
import com.m1zetzDev.swap.ui.theme.whiteForUi
import java.io.ByteArrayOutputStream
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material.icons.filled.Refresh
import com.m1zetzDev.swap.ui.theme.backgroundColorPurple3


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("RememberReturnType")
@Composable
fun HomePage(
) {

    val vmAddItem: HomeAddItemViewModel = viewModel()

    LaunchedEffect(Unit) {
        vmAddItem.getMyData()
    }
    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("My Announcements", fontSize = 22.sp) },
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
                            .fillMaxHeight(0.8f),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        // ТУТ КАРТОЧКИ
                        items(vmAddItem.listOfMyCards) { cards ->
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
                                        horizontalAlignment = Alignment.Start,
                                        modifier = Modifier.padding(top = 10.dp)) {
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
                        }
                    }
                }
            })


    }

    Box(
        contentAlignment = Alignment.BottomEnd,
        modifier = Modifier.fillMaxSize()
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Button(
                onClick = { vmAddItem.stateAddItem = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 130.dp),
                colors = ButtonDefaults.buttonColors(
                    contentColor = whiteForUi,
                    containerColor = backgroundColorPurple1
                )
            ) {
                Text("Add item")
            }

        }
        if (vmAddItem.stateAddItem) {
            AddItemWindow(
                state = true,
                onDismiss = { vmAddItem.stateAddItem = false },
                nameTextField = vmAddItem.messageName,
                onChangeName = { name ->
                    vmAddItem.obtainEvent(FieldsState.onChangeName(name))
                },
                descriptionTextField = vmAddItem.messageDescription,
                onChangeDescription = { description ->
                    vmAddItem.obtainEvent(FieldsState.onChangeDescription(description))
                }

            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddItemWindow(
    state: Boolean,
    onDismiss: () -> Unit,
    onChangeName: (name: String) -> Unit,
    onChangeDescription: (description: String) -> Unit,
    nameTextField: TextField,
    descriptionTextField: TextField,
) {
    val vmAddItem: HomeAddItemViewModel = viewModel()

    val sheetState = rememberModalBottomSheetState()

    val cv = LocalContext.current.contentResolver



    if (!state) return

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
                .heightIn(min = 800.dp, max = 1500.dp),
            verticalArrangement = Arrangement.Top
        ) {
            val launcher =
                rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
                    vmAddItem.messageUri = uri
//                    if (uri == null) return@rememberLauncherForActivityResult
                }

            Spacer(modifier = Modifier.size(10.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                //Enter photo

                Button(
                    modifier = Modifier.size(130.dp),
                    onClick = { launcher.launch(PickVisualMediaRequest(mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly)) },
                    colors = ButtonDefaults.buttonColors(
                        contentColor = whiteForUi,
                        containerColor = backgroundColorPurple1
                    ),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Column(
                        modifier = Modifier.height(130.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text("Enter ", fontSize = 30.sp)
                        Text("photo", fontSize = 30.sp)
                    }

                }


                //SendData

                Column(
                    horizontalAlignment = Alignment.End,
                ) {
                    Button(
                        onClick = { vmAddItem.sendData(cv) },
                        colors = ButtonDefaults.buttonColors(
                            contentColor = whiteForUi,
                            containerColor = green,
                        ),
                        shape = RoundedCornerShape(13.dp)
                    ) {
                        Text("Add item")
                    }
                }

            }


            Spacer(modifier = Modifier.size(10.dp))

            //Enter name

            OutlinedTextField(
                value = nameTextField.value,
                modifier = Modifier.padding(horizontal = 10.dp),
                onValueChange = { onChangeName(it) },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = backgroundColorPurple1,
                    unfocusedBorderColor = backgroundColorPurple1
                ),
                label = {
                    Text(
                        "Enter name",
                        color = backgroundColorPurple1,
                        fontWeight = FontWeight.Bold
                    )
                },
                shape = RoundedCornerShape(18.dp),
            )


            Spacer(modifier = Modifier.size(10.dp))

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.Start
            ) {

                val maxChar = 290

                //Enter description

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp),
                    value = descriptionTextField.value,
                    onValueChange = { if (it.length <= maxChar) onChangeDescription(it) },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = backgroundColorPurple1,
                        unfocusedBorderColor = backgroundColorPurple1
                    ),
                    label = {

                        Text(
                            "Enter description",
                            color = backgroundColorPurple1,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    shape = RoundedCornerShape(18.dp),
                )

                Spacer(modifier = Modifier.size(10.dp))


                Text(
                    "Select category:",
                    modifier = Modifier.padding(horizontal = 10.dp),
                    fontSize = 23.sp,
                    color = backgroundColorPurple1,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.size(10.dp))


                DropDownList()


                Spacer(modifier = Modifier.size(10.dp))


            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDownList() {

    val vmAddItem: HomeAddItemViewModel = viewModel()

    val listOfCategories = listOf(
        "Electronics",
        "Appliances",
        "Clothing & Shoes",
        "Toys",
        "Cars & Motorcycles",
        "Food & Drinks",
        "Books",
        "Stationery",
        "Furniture",
        "Home & Garden",
        "Beauty & Health",
        "Sports & Outdoors",
        "Pet Supplies",
        "Accessories",
        "Jewelry",
        "Tools",
        "Collectibles"
    )
    var isExpanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 10.dp),
        horizontalAlignment = Alignment.Start,

        ) {
        ExposedDropdownMenuBox(
            expanded = isExpanded,
            onExpandedChange = { isExpanded = !isExpanded },
        ) {
            OutlinedTextField(
                modifier = Modifier.menuAnchor(),
                value = selectedText,
                onValueChange = {},
                readOnly = true,
                shape = RoundedCornerShape(18.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = backgroundColorPurple1,
                    unfocusedBorderColor = backgroundColorPurple1
                ),
                textStyle = TextStyle(
                    fontSize = 20.sp,
                    color = backgroundColorPurple1
                )

            )


            ExposedDropdownMenu(expanded = isExpanded, onDismissRequest = { isExpanded = false }) {
                listOfCategories.forEachIndexed() { index, category ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = category,
                                fontSize = 20.sp,
                                color = backgroundColorPurple1
                            )
                        },
                        onClick = {
                            selectedText = listOfCategories[index]
                            isExpanded = false
                            vmAddItem.messageCategory.value = category
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                    )
                }
            }

        }
    }
}




