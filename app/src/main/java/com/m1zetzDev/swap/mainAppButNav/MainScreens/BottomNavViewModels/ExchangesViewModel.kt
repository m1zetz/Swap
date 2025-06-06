package com.m1zetzDev.swap.mainAppButNav.MainScreens.BottomNavViewModels

import android.util.Log
import androidx.compose.material3.Card
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlin.math.log

const val CARDSINEXCHANGE = "cardstoexchange"

class ExchangesViewModel : ViewModel() {

    val fireBase = Firebase.firestore
    var listOfCards by mutableStateOf(emptyList<Exchanges>())

    val acceptedEmail by mutableStateOf("")
    val acceptedName by mutableStateOf("")
    val acceptedDescription by mutableStateOf("")
    val acceptedCategory by mutableStateOf("")
    val acceptedUri by mutableStateOf("")

    val userEmailOther by mutableStateOf("")
    val nameOther by mutableStateOf("")
    val descriptionOther by mutableStateOf("")
    val categoryOther by mutableStateOf("")
    val imageUriOther by mutableStateOf("")

    fun getMyData(){
        val myEmail = Firebase.auth.currentUser?.email
        fireBase.collection(CARDSINEXCHANGE).whereEqualTo("userEmailOther", myEmail).get().addOnCompleteListener{ task ->
            if(task.isSuccessful){
                listOfCards = task.result.toObjects(Exchanges::class.java)
                Log.d("msg", listOfCards.toString())
            }
            else{
                Log.d("msg", "данные не получены")
            }
        }
    }

}
data class ExchangeCard(
    val name: String,
    val category: String,
    val description: String,
    val imageUri: String
)

data class Exchanges(
    val acceptedEmail: String = "",
    val acceptedName: String = "",
    val acceptedDescription: String = "",
    val acceptedCategory: String = "",
    val acceptedUri: String = "",

    val userEmailOther: String = "",
    val nameOther: String = "",
    val descriptionOther: String = "",
    val categoryOther: String = "",
    val imageUriOther: String = ""
)