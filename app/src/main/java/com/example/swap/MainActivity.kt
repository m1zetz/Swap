package com.example.swap

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text2.input.rememberTextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import com.example.swap.ui.theme.backgroundColorPurple1
import com.example.swap.ui.theme.backgroundColorPurple2
import com.example.swap.ui.theme.whiteForText
import com.example.swap.ui.theme.whiteOrange
import com.example.swap.ui.theme.whiteRed


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            enableEdgeToEdge()
            Box(modifier = Modifier.background(brush = Brush.verticalGradient(colors = listOf(backgroundColorPurple1, backgroundColorPurple2)))){

                Column(modifier = Modifier.fillMaxSize().padding(top = 80.dp), verticalArrangement = Arrangement.spacedBy(-45.dp)) {

                    Text("SWAP", fontSize = 160.sp, color = whiteForText, fontWeight = FontWeight.Bold)
                    Text("YOUR", fontSize = 145.sp, color = whiteOrange, fontWeight = FontWeight.Bold)
                    Text("LIFE", fontSize = 130.sp, color = whiteRed, fontWeight = FontWeight.Bold)

                }
                Column(
                    modifier = Modifier.fillMaxSize().padding(bottom = 175.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Bottom,
                    ){

                    Text("Welcome!", color = Color.White, fontSize = 25.sp, fontWeight = FontWeight.Bold)

                    val messageLogin = remember { mutableStateOf("") }
                    TextField(value = messageLogin.value,
                        onValueChange = {newText -> messageLogin.value = newText},
                        Modifier.padding(vertical = 15.dp),
                        colors = TextFieldDefaults.colors(
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedContainerColor = Color.White,
                            focusedContainerColor = Color.White
                        ),
                        label = { Text("Enter login", color = backgroundColorPurple1,) },
                        shape = RoundedCornerShape(25.dp)
                    )

                    val messagePassword = remember { mutableStateOf("") }
                    TextField(value = messagePassword.value,
                        onValueChange = {newText -> messagePassword.value = newText},
                        Modifier.padding(vertical = 15.dp),
                        colors = TextFieldDefaults.colors(
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedContainerColor = Color.White,
                            focusedContainerColor = Color.White
                        ),
                        label = { Text("Enter password", color = backgroundColorPurple1) },
                        shape = RoundedCornerShape(25.dp)
                    )


                    Button(onClick = {}, colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color(0xff222222)))
                    {
                        Text("Registration",textAlign = TextAlign.Center, color = backgroundColorPurple1)
                    }
                }
            }

        }
    }

}
