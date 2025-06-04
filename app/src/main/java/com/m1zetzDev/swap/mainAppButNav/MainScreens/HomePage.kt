package com.m1zetzDev.swap.mainAppButNav.MainScreens

import android.graphics.Bitmap
import androidx.activity.compose.rememberLauncherForActivityResult
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.m1zetzDev.swap.R
import com.m1zetzDev.swap.common.TextField
import com.m1zetzDev.swap.mainAppButNav.MainScreens.BottomNavViewModels.FieldsState
import com.m1zetzDev.swap.mainAppButNav.MainScreens.BottomNavViewModels.HomeAddItemViewModel
import com.m1zetzDev.swap.ui.theme.backgroundColorPurple1
import com.m1zetzDev.swap.ui.theme.green
import com.m1zetzDev.swap.ui.theme.whiteForUi


@Composable
fun HomePage(
) {
    val vmAddItem: HomeAddItemViewModel = viewModel()


    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .horizontalScroll(ScrollState(0)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
        }
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
            val result = remember { mutableStateOf<Bitmap?>(null) }
            val launcher =
                rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) {
                    result.value = it
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
                    onClick = { launcher.launch() },
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

                //Image

                result.value?.let { image ->
                    Image(image.asImageBitmap(), null, modifier = Modifier.size(120.dp))
                } ?: Image(
                    painter = painterResource(id = R.drawable.icon_camera),
                    null,
                    modifier = Modifier.size(100.dp)
                )

                //SendData

                Column(
                    horizontalAlignment = Alignment.End,
                ) {
                    Button(
                        onClick = { vmAddItem.sendData() },
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




