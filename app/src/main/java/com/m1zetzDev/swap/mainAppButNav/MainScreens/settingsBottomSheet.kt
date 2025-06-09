package com.m1zetzDev.swap.mainAppButNav.MainScreens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.m1zetzDev.swap.R
import com.m1zetzDev.swap.mainAppButNav.MainScreens.BottomNavViewModels.SettingsViewModel
import com.m1zetzDev.swap.ui.theme.SwapTheme
import com.m1zetzDev.swap.ui.theme.backgroundColorPurple1
import com.m1zetzDev.swap.ui.theme.backgroundColorPurple2
import com.m1zetzDev.swap.ui.theme.colorForBorder
import com.m1zetzDev.swap.ui.theme.grayForUi
import com.m1zetzDev.swap.ui.theme.red
import com.m1zetzDev.swap.ui.theme.whiteForUi

import java.time.MonthDay


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Settings(
    navController: NavController,
    viewModel: SettingsViewModel,
    onDismiss: () -> Unit,
    signOut: () -> Unit,
    toThemes: () -> Unit
) {

    val sheetState = rememberModalBottomSheetState()

    if (viewModel.showBottomSheet) {
        ModalBottomSheet(
            sheetState = sheetState,
            onDismissRequest = {
                viewModel.showBottomSheet = false
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
                        Text(stringResource(R.string.profile), fontSize = 35.sp)
                    }

                }
                Spacer(modifier = Modifier.size(10.dp))
                Button(
                    onClick = {toThemes()},
                    shape = RectangleShape,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        contentColor = backgroundColorPurple1,
                        containerColor = grayForUi
                    ),
                    border = BorderStroke(2.dp, color = colorForBorder)

                ) {

                    Row(modifier = Modifier.fillMaxWidth()) {
                        Text(stringResource(R.string.themes), fontSize = 35.sp)
                    }

                }
                Spacer(modifier = Modifier.size(10.dp))
                Column(verticalArrangement = Arrangement.Bottom) {
                    Button(
                        onClick = { signOut() },
                        shape = RectangleShape,
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            contentColor = red,
                            containerColor = grayForUi
                        ),
                        border = BorderStroke(2.dp, color = colorForBorder)

                    ) {

                        Row(modifier = Modifier.fillMaxWidth()) {
                            Text(stringResource(R.string.exit), fontSize = 35.sp)
                        }

                    }
                }
            }
        }
    }

}

@Composable
fun Themes(vmSettings: SettingsViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp).padding(top = 40.dp),
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                stringResource(R.string.light_theme),
                color = backgroundColorPurple1,
                fontSize = 25.sp
            )

            Spacer(Modifier.size(30.dp))

            Switch(
                checked = vmSettings.checkedLightTheme,  //ЛАЙТ
                onCheckedChange = {
                    vmSettings.checkedLightTheme = it
                    vmSettings.checkedDarkTheme = false
                    if(!it){
                        vmSettings.checkedDarkTheme = true
                    }
                },
                Modifier.size(25.dp)
            )
        }
        Spacer(Modifier.size(30.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                stringResource(R.string.dark_theme),
                color = backgroundColorPurple1,
                fontSize = 25.sp
            )

            Spacer(Modifier.size(30.dp))

            Switch(
                checked = vmSettings.checkedDarkTheme,
                onCheckedChange = {                         //ДАРК
                    vmSettings.checkedDarkTheme = it
                    vmSettings.checkedLightTheme = false
                    if(!it){
                        vmSettings.checkedLightTheme = true
                    }
                },
                Modifier.size(25.dp)
            )
        }
    }



}
