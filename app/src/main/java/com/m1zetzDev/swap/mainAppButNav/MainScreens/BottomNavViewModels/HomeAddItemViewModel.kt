package com.m1zetzDev.swap.mainAppButNav.MainScreens.BottomNavViewModels

import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import android.util.Log
import androidx.compose.runtime.MutableState
import com.m1zetzDev.swap.common.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.m1zetzDev.swap.auth.screens.auth5
import java.io.ByteArrayOutputStream

const val CARDS_COLLECTION = "cards"

class HomeAddItemViewModel : ViewModel() {


    val fireBase = Firebase.firestore

    var stateAddItem by mutableStateOf(false)

    var messageName by mutableStateOf(TextField())
    var messageDescription by mutableStateOf(TextField())
    var messageCategory by mutableStateOf(TextField())
    var messageUri by mutableStateOf<Uri?>(null)

    var dialogState by mutableStateOf(false)

    var selectedText by mutableStateOf("")

    var listOfCards by mutableStateOf(emptyList<Cards>())
    var listOfMyCards by mutableStateOf(emptyList<Cards>())

    fun removeCard(card: Cards) {
        listOfCards = listOfCards.toMutableList().apply {
            remove(card)
        }
    }

    fun obtainEvent(fieldsState: FieldsState) {
        when (fieldsState) {
            is FieldsState.onChangeName -> {
                messageName = messageName.copy(
                    value = fieldsState.name
                )
            }

            is FieldsState.onChangeDescription -> {
                messageDescription = messageDescription.copy(
                    value = fieldsState.description
                )
            }

            is FieldsState.onChangeCategory -> {
                messageCategory = messageCategory.copy(
                    value = fieldsState.category
                )
            }

        }
    }


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

    fun sendData(contentResolver: ContentResolver) {

        val uid = Firebase.auth.currentUser?.uid ?: return
        val uri = messageUri


        if (uri == null) {
            Log.e("sendData", "Image URI is null. Item not sent.")
            return
        }

        fireBase.collection(CARDS_COLLECTION).document().set(
            Cards(
                user_id = uid,
                name = messageName.value,
                description = messageDescription.value,
                category = messageCategory.value,
                imageUri = imageToBase64(uri, contentResolver)
            )
        ).addOnSuccessListener {
            getMyData()
            stateAddItem = false
            messageName.value = ""
            messageDescription.value = ""
            selectedText = ""
            messageUri = null
        }
    }

    fun imageToBase64(uri: Uri, contentResolver: ContentResolver): String {
        val inputStream = contentResolver.openInputStream(uri)
        val bitmap = BitmapFactory.decodeStream(inputStream)

        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, outputStream) // 50% качество
        val bytes = outputStream.toByteArray()
        inputStream?.close()
        Log.d("msg","Compressed size in bytes: ${bytes.size}")
        return Base64.encodeToString(bytes, Base64.NO_WRAP)
    }
}


data class Cards(
    val user_id: String = "",
    val name: String = "",
    val description: String = "",
    val category: String = "",
    val imageUri: String = ""
)

sealed class FieldsState {
    class onChangeName(val name: String) : FieldsState()
    class onChangeDescription(val description: String) : FieldsState()
    class onChangeCategory(val category: String) : FieldsState()
}