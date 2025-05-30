package com.m1zetzDev.swap.mainAppButNav.MainScreens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.m1zetzDev.swap.ui.theme.backgroundColorPurple1
import com.m1zetzDev.swap.ui.theme.backgroundColorPurple2
import com.m1zetzDev.swap.ui.theme.colorForBorder
import com.m1zetzDev.swap.ui.theme.grayForUi
import com.m1zetzDev.swap.ui.theme.red

import java.time.MonthDay

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Settings(state: Boolean, onDismiss: () -> Unit) {
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(state) }
    if (showBottomSheet) {
        Log.d("msg", "true")
        ModalBottomSheet(
            sheetState = sheetState,
            onDismissRequest = {
                showBottomSheet = false
                onDismiss()
            },
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = {},
                    shape = RectangleShape,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        contentColor = backgroundColorPurple1,
                        containerColor = grayForUi
                    ),
                    border = BorderStroke(2.dp, color = colorForBorder)

                ) {

                    Row(modifier = Modifier.fillMaxWidth()) {
                        Text("Profile", fontSize = 35.sp)
                    }

                }
                Spacer(modifier = Modifier.size(10.dp))
                Button(
                    onClick = {},
                    shape = RectangleShape,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        contentColor = backgroundColorPurple1,
                        containerColor = grayForUi
                    ),
                    border = BorderStroke(2.dp, color = colorForBorder)

                ) {

                    Row(modifier = Modifier.fillMaxWidth()) {
                        Text("Themes", fontSize = 35.sp)
                    }

                }
                Spacer(modifier = Modifier.size(10.dp))
                Column(verticalArrangement = Arrangement.Bottom) {
                    Button(
                        onClick = {},
                        shape = RectangleShape,
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            contentColor = red,
                            containerColor = grayForUi
                        ),
                        border = BorderStroke(2.dp, color = colorForBorder)

                    ) {

                        Row(modifier = Modifier.fillMaxWidth()) {
                            Text("Exit", fontSize = 35.sp)
                        }

                    }
                }
            }
        }
    }


}
