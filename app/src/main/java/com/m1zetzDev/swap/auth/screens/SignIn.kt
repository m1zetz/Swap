package com.m1zetzDev.swap.auth.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.m1zetzDev.swap.R
import com.m1zetzDev.swap.common.TextField
import com.m1zetzDev.swap.ui.theme.backgroundColorPurple1
import com.m1zetzDev.swap.ui.theme.backgroundColorPurple2
import com.m1zetzDev.swap.ui.theme.whiteForUi


val auth5 = FirebaseAuth.getInstance()


@Composable
fun ScreenSignIn(
    toSignUp: () -> Unit,
    onLogin: () -> Unit,
    emailTextField: TextField,
    passwordTextField: TextField,
    onChangedEmail: (email: String) -> Unit,
    onChangedPassword: (password: String) -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        backgroundColorPurple1, backgroundColorPurple2
                    )
                )
            )
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 80.dp)
        ) {

            Image(
                bitmap = ImageBitmap.imageResource(
                    R.drawable.swap_your_life
                ),
                contentDescription = "",
                contentScale = ContentScale.Fit
            )

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {

                Text(
                    stringResource(R.string.welcome),
                    color = whiteForUi,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                )





                TextField(
                    value = emailTextField.value,
                    onValueChange = { onChangedEmail(it) },
                    Modifier.padding(vertical = 15.dp),
                    colors = TextFieldDefaults.colors(
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedContainerColor = whiteForUi,
                        focusedContainerColor = whiteForUi
                    ),
                    label = {
                        Text(
                            stringResource(R.string.enter_email),
                            color = backgroundColorPurple1,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    shape = RoundedCornerShape(25.dp)
                )


                TextField(
                    value = passwordTextField.value,
                    onValueChange = { onChangedPassword(it) },
                    Modifier.padding(vertical = 15.dp),
                    colors = TextFieldDefaults.colors(
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedContainerColor = whiteForUi,
                        focusedContainerColor = whiteForUi
                    ),
                    label = {
                        Text(
                            stringResource(R.string.enter_password),
                            color = backgroundColorPurple1,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    shape = RoundedCornerShape(25.dp)
                )


                Button(
                    onClick = { onLogin() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = whiteForUi,
                    )
                )
                {
                    Text(
                        stringResource(R.string.sign_in),
                        textAlign = TextAlign.Center,
                        color = backgroundColorPurple1,
                        fontWeight = FontWeight.Bold
                    )
                }

                Button(
                    onClick = {
                        toSignUp()
                    }, colors = ButtonDefaults.buttonColors(
                        containerColor = whiteForUi
                    )
                )
                {
                    Text(
                        stringResource(R.string.sign_up),
                        textAlign = TextAlign.Center,
                        color = backgroundColorPurple1,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

        }


    }
}





