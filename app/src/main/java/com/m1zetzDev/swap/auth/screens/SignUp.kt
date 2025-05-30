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
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.m1zetzDev.swap.R
import com.m1zetzDev.swap.ui.theme.backgroundColorPurple1
import com.m1zetzDev.swap.ui.theme.backgroundColorPurple2
import com.m1zetzDev.swap.ui.theme.whiteForUi

@Composable
fun ScreenSignUp(onBackPress: () -> Unit) {

    Box(
        modifier = Modifier
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        backgroundColorPurple1, backgroundColorPurple2
                    )
                )
            )
            .padding(80.dp)
            .fillMaxSize()
    ) {

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                bitmap = ImageBitmap.imageResource(R.drawable.registration),
                contentDescription = "",
                modifier = Modifier.padding(start = 25.dp)
            )

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {

                Text(
                    "Welcome! Register in our application.",
                    color = whiteForUi,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

                val auth = Firebase.auth

                val messageEmail = remember { mutableStateOf("") }
                val messagePassword = remember { mutableStateOf("") }
                val messageName = remember { mutableStateOf("") }


                TextField(
                    value = messageEmail.value,
                    onValueChange = { messageEmail.value = it },
                    Modifier.padding(vertical = 15.dp),
                    colors = TextFieldDefaults.colors(
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedContainerColor = whiteForUi,
                        focusedContainerColor = whiteForUi
                    ),
                    label = {
                        Text(
                            "Enter email",
                            color = backgroundColorPurple1,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    shape = RoundedCornerShape(25.dp)
                )


                TextField(
                    value = messagePassword.value,
                    onValueChange = { messagePassword.value = it },
                    Modifier.padding(vertical = 15.dp),
                    colors = TextFieldDefaults.colors(
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedContainerColor = whiteForUi,
                        focusedContainerColor = whiteForUi
                    ),
                    label = {
                        Text(
                            "Enter password",
                            color = backgroundColorPurple1,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    shape = RoundedCornerShape(25.dp)
                )


                Button(
                    onClick = { signUp(auth, messageEmail.value, messagePassword.value) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = whiteForUi,
                        contentColor = Color(0xff222222)
                    ),
                )
                {
                    Text(
                        "Sign Up",
                        textAlign = TextAlign.Center,
                        color = backgroundColorPurple1,
                        fontWeight = FontWeight.Bold
                    )
                }

                Button(
                    onClick = {
                        onBackPress()
                    }, colors = ButtonDefaults.buttonColors(
                        containerColor = whiteForUi,
                    )
                )
                {
                    Text(
                        "Back to Sign In",
                        textAlign = TextAlign.Center,
                        color = backgroundColorPurple1,
                        fontWeight = FontWeight.Bold
                    )
                }
            }


        }

    }
}


private fun signUp(firebaseAuth: FirebaseAuth, email: String, password: String) {
    firebaseAuth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener {
            if (it.isSuccessful) {
                Log.d("MyLog", "Sign Up is Successful!")
            } else {
                Log.d("MyLog", "Sign Up is Failed!")
            }
        }
}
