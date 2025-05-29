package com.m1zetzDev.swap

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.m1zetzDev.swap.auth.AuthViewModel
import com.m1zetzDev.swap.auth.screens.ScreenSignIn
import com.m1zetzDev.swap.auth.screens.ScreenSignUp
import com.m1zetzDev.swap.mainAppButNav.AppBottomNavigation


class MainActivity : ComponentActivity() {
    @SuppressLint("ViewModelConstructorInComposable")
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

                    val vm = AuthViewModel()

                    ScreenSignIn(
                        onLogin = { email, password ->
                            Firebase.auth.signInWithEmailAndPassword(email, password)
                                .addOnCompleteListener {
                                    if (it.isSuccessful) {
                                        Log.d("MyLog", "Sign In is Successful!")
                                        navController.navigate("main_window_screen")
                                    } else {
                                        Log.d("MyLog", "Sign In is Failed!")
                                    }
                                }
                        },
                        toSignUp = {
                            navController.navigate("screen_sign_up")
                        }
                    )

                }
                composable("screen_sign_up") {
                    ScreenSignUp(
                        onBackPress = {
                            navController.popBackStack()
                        }
                    )
                }
                composable("main_window_screen") {
                    AppBottomNavigation(navController)
                }
            }
        }
    }
}