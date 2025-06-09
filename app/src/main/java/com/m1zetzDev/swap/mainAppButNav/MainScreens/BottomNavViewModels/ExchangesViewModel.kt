package com.m1zetzDev.swap.mainAppButNav.MainScreens.BottomNavViewModels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

const val CARDS_IN_EXCHANGE = "cardstoexchange"

class ExchangesViewModel : ViewModel() {

    val fireBase = Firebase.firestore
    var listOfCards by mutableStateOf(emptyList<Exchanges>())


    fun getMyData() {
        val myId = Firebase.auth.currentUser?.uid
        fireBase.collection(CARDS_IN_EXCHANGE).whereEqualTo("userIdOther", myId).get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    listOfCards = task.result.toObjects(Exchanges::class.java)
                } else {
                    Log.d("msg", "данные не получены")
                }
            }
    }

    fun acceptExchange(exchange: Exchanges) {
        Firebase.firestore.collection("successfulExchanges")
            .add(exchange)
            .addOnSuccessListener {
            }
            .addOnFailureListener {
                Log.e("Firebase", "Ошибка при сохранении обмена", it)
            }
    }


    data class Exchanges(
        val acceptedId: String = "",
        val acceptedName: String = "",
        val acceptedDescription: String = "",
        val acceptedCategory: String = "",
        val acceptedUri: String = "",

        val userIdOther: String = "",
        val nameOther: String = "",
        val descriptionOther: String = "",
        val categoryOther: String = "",
        val imageUriOther: String = "",

    )
}