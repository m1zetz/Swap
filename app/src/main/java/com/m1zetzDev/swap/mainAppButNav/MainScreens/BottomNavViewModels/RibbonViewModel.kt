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

    var messageUri by mutableStateOf("")
    var messageName by mutableStateOf("")
    var messageDescription by mutableStateOf("")           //my
    var messageCategory by mutableStateOf("")

    var emailOther by mutableStateOf("")
    var uriOther by mutableStateOf("")
    var nameOther by mutableStateOf("")                    //not my
    var descriptionOther by mutableStateOf("")
    var categoryOther by mutableStateOf("")


    var listOfCards by mutableStateOf(emptyList<Cards>())
    var listOfMyCards by mutableStateOf(emptyList<Cards>())

    fun getMyData() {
        val uid = Firebase.auth.currentUser?.uid ?: return
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

    fun imageToBase64(uri: Uri, contentResolver: ContentResolver): String {
        val inputStream = contentResolver.openInputStream(uri)
        val bytes = inputStream?.readBytes()
        return bytes?.let {
            Base64.encodeToString(it, Base64.DEFAULT)
        } ?: ""
    }

    fun sendData(
        name: String,
        description: String,
        category: String,
        uri: String,
    ) {

        val acceptedUserEmail = Firebase.auth.currentUser?.email


        val userEmailOther = emailOther
        val imageUriOther = uriOther


        if (uri == null) {
            Log.e("sendData", "Image URI is null. Item not sent.")
            return
        }
        if (imageUriOther == null) {
            Log.e("sendData", "Image URI is null. Item not sent.")
            return
        }

        fireBase.collection(CARDS_TO_EXCHANGE).document().set(
            ToExchange(
                acceptedEmail = acceptedUserEmail.toString(),
                acceptedName = name,
                acceptedDescription = description,
                acceptedCategory = category,
                acceptedUri = uri,

                userEmailOther = userEmailOther,
                nameOther = nameOther,
                descriptionOther = descriptionOther,
                categoryOther = categoryOther,
                imageUriOther = uriOther
            )
        )
    }
}

data class ToExchange(
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