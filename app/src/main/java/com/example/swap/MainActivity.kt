package com.example.swap

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.swap.ui.theme.SwapTheme
import com.example.swap.ui.theme.backgroundColorPurple
import com.example.swap.ui.theme.whiteRed

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Column(modifier = Modifier.background(backgroundColorPurple).fillMaxSize()) {

                Text("SWAP", fontSize = 160.sp, color = Color.White, fontWeight = FontWeight.Bold)
                Text("YOUR", fontSize = 140.sp, color = Color.White, fontWeight = FontWeight.Bold)
                Text("LIFE", fontSize = 120.sp, color = Color.White, fontWeight = FontWeight.Bold)



            }
            Column(
                modifier = Modifier.fillMaxSize().padding(bottom = 145.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom,

                ){

                OutlinedTextField(value = "Введите логин",
                    onValueChange = {},
                    Modifier.padding(vertical = 15.dp),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color(0xffeeeeee),
                        unfocusedTextColor = Color(0xff888888),
                        focusedContainerColor = Color.White,
                        focusedTextColor = Color(0xff222222)
                    ),
                    shape = RoundedCornerShape(25.dp)
                    )

                OutlinedTextField(value = "Введите пароль",
                    onValueChange = {},
                    Modifier.padding(vertical = 15.dp),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color(0xffeeeeee),
                        unfocusedTextColor = Color(0xff888888),
                        focusedContainerColor = Color.White,
                        focusedTextColor = Color(0xff222222)
                    ),
                    shape = RoundedCornerShape(25.dp)
                )


                Button(onClick = {}, colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color(0xff222222)))
                {
                    Text("Регистрация",textAlign = TextAlign.Center)
                }
            }
        }
    }

}
