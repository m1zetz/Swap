package com.m1zetzDev.swap.mainAppButNav.MainScreens.BottomNavViewModels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.m1zetzDev.swap.common.TextField

const val SUCCESSFUL_EXCHANGES = "successfulExchanges"

class ChatsViewModel : ViewModel() {
    val database = FirebaseDatabase.getInstance("https://swapdb-85cd5-default-rtdb.europe-west1.firebasedatabase.app/")

    val fireBase = Firebase.firestore

    var currentId: String? = null

    var messageText by mutableStateOf(TextField())
    val otherId = mutableStateOf("")
    val otherUri = mutableStateOf("")

    fun selectUserForChat(uid: String, uri: String) {
        otherId.value = uid
        otherUri.value = uri
    }

    fun updateCurrentId() {
        currentId = Firebase.auth.currentUser?.uid
    }

    fun createChatId(currentId: String, otherId: String) : String{
        val modifiedCurrentId = currentId.replace(".","_")
        val modifiedCOtherId = otherId.replace(".","_")
        val sorted = listOf(modifiedCurrentId, modifiedCOtherId).sorted()
        return "${sorted[0]}_${sorted[1]}"
    }

    fun sendMessage(currentId: String, otherId: String){
        val message = messageText.value.trim()
        if (message.isBlank()){
            return
        }

        messageText = messageText.copy(value = "")

        val chatId = createChatId(currentId, otherId)

        val messageData = mapOf(
            "senderId" to currentId,
            "text" to message,
            "timestamp" to System.currentTimeMillis()
        )

        database.getReference("chats")
            .child(chatId)
            .child("messages")
            .push()
            .setValue(messageData)
    }

    val messagesList = mutableStateListOf<ChatMessage>()

    fun getMessages(currentId: String, otherId: String){
        val chatId = createChatId(currentId, otherId)

        database.getReference("chats").child(chatId).child("messages")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    Log.d("ChatsViewModel", "onDataChange triggered. Children count: ${snapshot.childrenCount}")
                    messagesList.clear()
                    for (msgSnapshot in snapshot.children) {
                        val sender = msgSnapshot.child("senderId").getValue(String::class.java) ?: ""
                        val message = msgSnapshot.child("text").getValue(String::class.java) ?: ""
                        val timestamp = msgSnapshot.child("timestamp").getValue(Long::class.java) ?: 0L

                        Log.d("ChatsViewModel", "Message loaded: $sender - $message")

                        messagesList.add(ChatMessage(sender, message, timestamp))
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("ChatsViewModel", "Failed to load messages: ${error.message}")
                }
            })


    }




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
        val myId = Firebase.auth.currentUser?.uid ?: return

        val allResults = mutableListOf<ExchangesViewModel.Exchanges>()

        fireBase.collection(SUCCESSFUL_EXCHANGES)
            .whereEqualTo("acceptedId", myId)
            .get()
            .addOnSuccessListener { acceptedResult ->
                allResults.addAll(acceptedResult.toObjects(ExchangesViewModel.Exchanges::class.java))

                fireBase.collection(SUCCESSFUL_EXCHANGES)
                    .whereEqualTo("userIdOther", myId)
                    .get()
                    .addOnSuccessListener { otherResult ->
                        allResults.addAll(otherResult.toObjects(ExchangesViewModel.Exchanges::class.java))

                        listOfCardsUsers.clear()
                        listOfCardsUsers.addAll(allResults)
                    }
                    .addOnFailureListener {
                        Log.d("msg", "Ошибка при получении userIdOther: $it")
                    }
            }
            .addOnFailureListener {
                Log.d("msg", "Ошибка при получении acceptedId: $it")
            }
    }


}
data class ChatMessage(
    val senderId: String,
    val text: String,
    val timestamp: Long
)
sealed class EnterEvent() {
    class OnChangeMessage(val message: String) : EnterEvent()
}


