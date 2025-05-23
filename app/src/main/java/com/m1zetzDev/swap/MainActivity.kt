package com.m1zetzDev.swap

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.m1zetzDev.swap.ui.ScreenSignIn
import com.m1zetzDev.swap.ui.ScreenSignUp
import com.m1zetzDev.swap.ui.theme.backgroundColorPurple1
import com.m1zetzDev.swap.ui.theme.backgroundColorPurple2
import com.m1zetzDev.swap.ui.theme.whiteForText
import com.m1zetzDev.swap.ui.theme.whiteOrange
import com.m1zetzDev.swap.ui.theme.whiteRed
import androidx.navigation.NavHost as NavHost1


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            enableEdgeToEdge()
            val navController = rememberNavController()
            NavHost(
                navController = navController,
                startDestination = "screen_sign_in"
            ) {
                composable("screen_sign_in") {
                    ScreenSignIn {
                        navController.navigate("screen_sign_up")
                    }
                }
                composable("screen_sign_up") {
                    ScreenSignUp {
                        navController.navigate("screen_sign_in")
                    }
                }
            }

//            enableEdgeToEdge()
//            Box(modifier = Modifier.background(brush = Brush.verticalGradient(colors = listOf(backgroundColorPurple1, backgroundColorPurple2)))){
//
//                Column(modifier = Modifier.fillMaxSize().padding(top = 80.dp), verticalArrangement = Arrangement.spacedBy(-45.dp)) {
//
//                    Text("SWAP", fontSize = 160.sp, color = whiteForText, fontWeight = FontWeight.Bold)
//                    Text("YOUR", fontSize = 145.sp, color = whiteOrange, fontWeight = FontWeight.Bold)
//                    Text("LIFE", fontSize = 130.sp, color = whiteRed, fontWeight = FontWeight.Bold)
//
//                }
//                Column(
//                    modifier = Modifier.fillMaxSize().padding(bottom = 175.dp),
//                    horizontalAlignment = Alignment.CenterHorizontally,
//                    verticalArrangement = Arrangement.Bottom,
//                    ){
//
//                    Text("Welcome!", color = Color.White, fontSize = 25.sp, fontWeight = FontWeight.Bold)
//
//                    val auth = Firebase.auth
//
//                    val messageEmail = remember { mutableStateOf("") }
//                    val messagePassword = remember { mutableStateOf("") }
//
//                    TextField(value = messageEmail.value,
//                        onValueChange = {messageEmail.value = it},
//                        Modifier.padding(vertical = 15.dp),
//                        colors = TextFieldDefaults.colors(
//                            unfocusedIndicatorColor = Color.Transparent,
//                            focusedIndicatorColor = Color.Transparent,
//                            unfocusedContainerColor = Color.White,
//                            focusedContainerColor = Color.White
//                        ),
//                        label = { Text("Enter email", color = backgroundColorPurple1,) },
//                        shape = RoundedCornerShape(25.dp)
//                    )
//
//
//                    TextField(value = messagePassword.value,
//                        onValueChange = {messagePassword.value = it},
//                        Modifier.padding(vertical = 15.dp),
//                        colors = TextFieldDefaults.colors(
//                            unfocusedIndicatorColor = Color.Transparent,
//                            focusedIndicatorColor = Color.Transparent,
//                            unfocusedContainerColor = Color.White,
//                            focusedContainerColor = Color.White
//                        ),
//                        label = { Text("Enter password", color = backgroundColorPurple1) },
//                        shape = RoundedCornerShape(25.dp)
//                    )
//
//
//                    Button(onClick = { signUp(auth, messageEmail.value, messagePassword.value)}, colors = ButtonDefaults.buttonColors(
//                        containerColor = Color.White,
//                        contentColor = Color(0xff222222)),)
//                    {
//                        Text("Sign In",textAlign = TextAlign.Center, color = backgroundColorPurple1)
//                    }
//
//                    Button(onClick = {}, colors = ButtonDefaults.buttonColors(
//                        containerColor = Color.White,
//                        contentColor = Color(0xff222222)))
//                    {
//                        Text("Sign Up",textAlign = TextAlign.Center, color = backgroundColorPurple1)
//                    }
//
//
//                    }
//
//                }
//            }

//        }
//    }


//private fun signUp(firebaseAuth: FirebaseAuth, email: String, password: String){
//    firebaseAuth.createUserWithEmailAndPassword(email, password)
//        .addOnCompleteListener {
//            if(it.isSuccessful){
//                Log.d("MyLog", "Sign Up is Successful!")
//            } else{
//                Log.d("MyLog", "Sign Up is Failed!")
//            }
//        }
//}
//
//private fun signIn(firebaseAuth: FirebaseAuth, email: String, password: String){
//    firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener{
//        if(it.isSuccessful){
//            Log.d("MyLog", "Sign In is Successful!")
//        } else{
//            Log.d("MyLog", "Sign In is Failed!")
//        }
//    }
//}
        }
    }
}