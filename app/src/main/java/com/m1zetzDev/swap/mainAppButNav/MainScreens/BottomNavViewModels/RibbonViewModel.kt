package com.m1zetzDev.swap.mainAppButNav.MainScreens.BottomNavViewModels

import android.content.ContentResolver
import android.net.Uri
import android.util.Base64
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.m1zetzDev.swap.common.TextField

const val CARDS_TO_EXCHANGE = "cardstoexchange"

class RibbonViewModel : ViewModel() {

    var stateOfBotttomSheet by mutableStateOf(false)


    val fireBase = Firebase.firestore


    var uriOther by mutableStateOf("")
    var uidOther by mutableStateOf("")
    var nameOther by mutableStateOf("")
    var descriptionOther by mutableStateOf("")
    var categoryOther by mutableStateOf("")


    var listOfCards by mutableStateOf(emptyList<Cards>())
    var listOfMyCards by mutableStateOf(emptyList<Cards>())

    fun getMyData() {
        val uid = Firebase.auth.currentUser?.uid
        fireBase.collection(CARDS_COLLECTION).whereEqualTo("user_id", uid).get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    listOfMyCards = task.result.toObjects(Cards::class.java)
                } else {
                    Log.d("msg", "данные не получены")
                }
            }
    }

    fun removeCard(card: Cards) {
        listOfCards = listOfCards.toMutableList().apply {
            remove(card)
        }
    }

    fun getData() {
        val uid = Firebase.auth.currentUser?.uid ?: return
        fireBase.collection(CARDS_COLLECTION).get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                var allCards by mutableStateOf(emptyList<Cards>())
                allCards = task.result.toObjects(Cards::class.java)
                listOfCards = allCards.filter { it.user_id != uid }
            } else {
                Log.d("msg", "данные не получены")
            }
        }
    }


    fun sendData(
        name: String,
        description: String,
        category: String,
        uri: String,
    ) {

        val acceptedUserId = Firebase.auth.currentUser?.uid

        fireBase.collection(CARDS_TO_EXCHANGE).document().set(
            ToExchange(
                acceptedId = acceptedUserId.toString(),
                acceptedName = name,
                acceptedDescription = description,
                acceptedCategory = category,
                acceptedUri = uri,

                userIdOther = uidOther,
                nameOther = nameOther,
                descriptionOther = descriptionOther,
                categoryOther = categoryOther,
                imageUriOther = uriOther
            )
        )
    }
}

data class ToExchange(
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