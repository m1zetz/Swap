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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.m1zetzDev.swap.common.TextField
import com.m1zetzDev.swap.mainAppButNav.MainScreens.BottomNavViewModels.FieldsState
import com.m1zetzDev.swap.mainAppButNav.MainScreens.BottomNavViewModels.HomeAddItemViewModel
import com.m1zetzDev.swap.ui.theme.backgroundColorPurple1
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
    descriptionTextField: TextField
) {
    val sheetState = rememberModalBottomSheetState()

    if (!state) return

    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = {
            onDismiss()
        }
    ) {

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top
        ) {
            val result = remember { mutableStateOf<Bitmap?>(null) }
            val launcher =
                rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) {
                    result.value = it
                }

            Spacer(modifier = Modifier.size(10.dp))
            Row(horizontalArrangement = Arrangement.Start) {
                Button(
                    modifier = Modifier.size(130.dp),
                    onClick = { launcher.launch() },
                    colors = ButtonDefaults.buttonColors(
                        contentColor = whiteForUi,
                        containerColor = backgroundColorPurple1
                    ),
                    shape = RectangleShape
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
                result.value?.let { image ->
                    Image(image.asImageBitmap(), null, modifier = Modifier.size(130.dp))
                }
            }


            Spacer(modifier = Modifier.size(10.dp))

            TextField(
                value = nameTextField.value,
                onValueChange = { onChangeName(it) },
                colors = TextFieldDefaults.colors(
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedContainerColor = whiteForUi,
                    focusedContainerColor = whiteForUi
                ),
                label = {
                    Text(
                        "Enter name",
                        color = backgroundColorPurple1,
                        fontWeight = FontWeight.Bold
                    )
                },
                shape = RectangleShape
            )


            Spacer(modifier = Modifier.size(10.dp))

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                val maxChar = 290

                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = descriptionTextField.value,
                    onValueChange = { if (it.length <= maxChar) onChangeDescription(it) },
                    colors = TextFieldDefaults.colors(
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedContainerColor = whiteForUi,
                        focusedContainerColor = whiteForUi
                    ),
                    label = {
                        Text(
                            "Enter description",
                            color = backgroundColorPurple1,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    shape = RectangleShape,
                )

                Spacer(modifier = Modifier.size(10.dp))

                Button(
                    onClick = { },
                    modifier = Modifier
                        .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        contentColor = whiteForUi,
                        containerColor = backgroundColorPurple1
                    )
                ) {
                    Text("Add item")
                }
            }

        }
    }
}




