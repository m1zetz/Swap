package com.m1zetzDev.swap

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.ModalBottomSheet
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.m1zetzDev.swap.auth.AuthEvent
import com.m1zetzDev.swap.auth.RegEvent
import com.m1zetzDev.swap.auth.SignInViewModel
import com.m1zetzDev.swap.auth.SignUpViewModel
import com.m1zetzDev.swap.auth.screens.ScreenSignIn
import com.m1zetzDev.swap.auth.screens.ScreenSignUp
import com.m1zetzDev.swap.mainAppButNav.AppBottomNavigation
import com.m1zetzDev.swap.mainAppButNav.MainScreens.BottomNavViewModels.ChatsViewModel
import com.m1zetzDev.swap.mainAppButNav.MainScreens.BottomNavViewModels.EnterEvent
import com.m1zetzDev.swap.mainAppButNav.MainScreens.BottomNavViewModels.SettingsViewModel
import com.m1zetzDev.swap.mainAppButNav.MainScreens.Settings
import com.m1zetzDev.swap.mainAppButNav.MainScreens.ChatScreen




class MainActivityNavigation : ComponentActivity() {
    @SuppressLint("ViewModelConstructorInComposable")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val mainWindowScreen = "main_window_screen"
            val screenSignIn = "screen_sign_in"
            var choiceStartScreen = ""

            val auth = Firebase.auth

            val vmSignIn: SignInViewModel = viewModel()
            val vmSignUp: SignUpViewModel = viewModel()
            val vmSettings: SettingsViewModel = viewModel()
            val vmChats: ChatsViewModel = viewModel()

            enableEdgeToEdge()

            val navController = rememberNavController()

            fun performSignOut() {
                Firebase.auth.signOut()
                navController.navigate("screen_sign_in")
                vmSignIn.messageEmail.value = ""
                vmSignIn.messagePassword.value = ""
            }

            NavHost(
                navController = navController,
                startDestination = mainWindowScreen
            ) {
                composable("screen_sign_in") {


                    ScreenSignIn(
                        onLogin = {
                            auth.signInWithEmailAndPassword(
                                vmSignIn.messageEmail.value,
                                vmSignIn.messagePassword.value
                            )
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
                        },
                        emailTextField = vmSignIn.messageEmail,
                        onChangedEmail = { email ->
                            vmSignIn.obtainEvent(AuthEvent.OnChangeEmail(email))
                        },
                        passwordTextField = vmSignIn.messagePassword,
                        onChangedPassword = { password ->
                            vmSignIn.obtainEvent(AuthEvent.OnChangePassword(password))
                        }

                    )


                }
                composable("screen_sign_up") {
                    ScreenSignUp(
                        signUp = {
                            auth.createUserWithEmailAndPassword(
                                vmSignUp.messageEmail.value,
                                vmSignUp.messagePassword.value
                            )
                                .addOnCompleteListener {
                                    if (it.isSuccessful) {
                                        Log.d("MyLog", "Sign Up is Successful!")
                                        navController.navigate("screen_sign_in")
                                    } else {
                                        Log.d("MyLog", "Sign Up is Failed!")
                                    }
                                }
                        },
                        onBackPress = {
                            navController.popBackStack()
                        },
                        emailTextField = vmSignUp.messageEmail,
                        onChangeEmail = { email ->
                            vmSignUp.obtainEvent(RegEvent.OnChangeEmail(email))
                        },
                        passwordTextField = vmSignUp.messagePassword,
                        onChangePassword = { password ->
                            vmSignUp.obtainEvent(RegEvent.OnChangePassword(password))
                        }
                    )
                }
                composable("main_window_screen") {
                    AppBottomNavigation(
                        signOut = { performSignOut() },
                        navController = navController,
                        vmChats = vmChats
                    )
                }
                composable("settings_bottom_sheet") {
                    Settings(
                        signOut = { performSignOut() },
                        onDismiss = { vmSettings.showBottomSheet = false },
                        viewModel = vmSettings
                    )
                }
                composable("chat_screen") {
                    ChatScreen(
                        messageTextField = vmChats.messageText,
                        onChangeMessage = { message ->
                            vmChats.obtainEvent((EnterEvent.OnChangeMessage(message)))
                        },
                        vmChats = vmChats

                    )
                }

            }
        }


    }
}


