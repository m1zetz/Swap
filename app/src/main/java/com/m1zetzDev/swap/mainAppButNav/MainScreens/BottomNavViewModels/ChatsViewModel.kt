package com.m1zetzDev.swap.mainAppButNav.MainScreens.BottomNavViewModels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.m1zetzDev.swap.auth.AuthEvent
import com.m1zetzDev.swap.common.TextField

const val SUCCESSFUL_EXCHANGES = "successfulExchanges"

class ChatsViewModel : ViewModel() {
    val database = Firebase.database.reference

    val fireBase = Firebase.firestore

    val currentEmail = Firebase.auth.currentUser?.email


    val otherEmail = mutableStateOf("")

    val otherUri = mutableStateOf("")

    fun selectUserForChat(email: String, uri: String) {
        otherEmail.value = email
        otherUri.value = uri
    }

    fun sendMessage(currentEmail: String, otherEmail: String){

        val modifiedCurrentEmail = currentEmail.replace(".","_")
        val modifiedCOtherEmail = otherEmail.replace(".","_")
        val checkId = "${modifiedCurrentEmail}_${modifiedCOtherEmail}"

        database.child("chats").child("userA_userB").setValue(checkId)

    }

    fun getMessages(){

    }

    var messageText by mutableStateOf(TextField())

    fun obtainEvent(enterEvent: EnterEvent){
        when(enterEvent){
            is EnterEvent.OnChangeMessage -> {
                messageText = messageText.copy(
                    value = enterEvent.message
                )
            }

        }
    }

    var listOfCardsUsers = mutableStateListOf<ExchangesViewModel.Exchanges>()


    fun getAllMySuccessfulExchanges() {
        val myEmail = Firebase.auth.currentUser?.email ?: return

        val allResults = mutableListOf<ExchangesViewModel.Exchanges>()

        fireBase.collection(SUCCESSFUL_EXCHANGES)
            .whereEqualTo("acceptedEmail", myEmail)
            .get()
            .addOnSuccessListener { acceptedResult ->
                allResults.addAll(acceptedResult.toObjects(ExchangesViewModel.Exchanges::class.java))

                fireBase.collection(SUCCESSFUL_EXCHANGES)
                    .whereEqualTo("userEmailOther", myEmail)
                    .get()
                    .addOnSuccessListener { otherResult ->
                        allResults.addAll(otherResult.toObjects(ExchangesViewModel.Exchanges::class.java))

                        listOfCardsUsers.clear()
                        listOfCardsUsers.addAll(allResults)
                    }
                    .addOnFailureListener {
                        Log.d("msg", "Ошибка при получении userEmailOther: $it")
                    }
            }
            .addOnFailureListener {
                Log.d("msg", "Ошибка при получении acceptedEmail: $it")
            }
    }


}

sealed class EnterEvent() {
    class OnChangeMessage(val message: String) : EnterEvent()
}

data class Chats(
    val userEmail1: String = "",
    val userEmail2: String = "",

    val messageEmail1: String = "",
    val messageEmail2: String = ""

)
