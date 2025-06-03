package com.m1zetzDev.swap

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.m1zetzDev.swap.auth.AuthEvent
import com.m1zetzDev.swap.auth.AuthViewModel
import com.m1zetzDev.swap.auth.screens.ScreenSignIn
import com.m1zetzDev.swap.auth.screens.ScreenSignUp
import com.m1zetzDev.swap.mainAppButNav.AppBottomNavigation


class MainActivityNavigation : ComponentActivity() {
    @SuppressLint("ViewModelConstructorInComposable")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val mainWindowScreen = "main_window_screen"
            val screenSignIn = "screen_sign_in"
            var choiceStartScreen = ""

            enableEdgeToEdge()
            val navController = rememberNavController()
            NavHost(
                navController = navController,
                startDestination = screenSignIn
            ) {
                composable("screen_sign_in") {

                    val vm: AuthViewModel = viewModel()



                    ScreenSignIn(
                        onLogin = {
                            val auth = Firebase.auth
                            auth.signInWithEmailAndPassword(vm.messageEmail.value, vm.messagePassword.value)
                                .addOnCompleteListener {
                                    if (it.isSuccessful) {
                                        Log.d("MyLog", "Sign In is Successful!")
                                        navController.navigate("main_window_screen")
                                    } else {
                                        Log.d("MyLog", "Sign In is Failed!")
                                    }
                                }},
                        toSignUp = {
                            navController.navigate("screen_sign_up")
                        },
                        emailTextField = vm.messageEmail,
                        onChangedEmail = { email ->
                            vm.obtainEvent(AuthEvent.OnChangeEmail(email))
                        },
                        passwordTextField = vm.messagePassword,
                        onChangedPassword = { password ->
                            vm.obtainEvent(AuthEvent.OnChangePassword(password))
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
                    AppBottomNavigation()
                }


            }
        }
    }
}