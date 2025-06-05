package com.m1zetzDev.swap.mainAppButNav.MainScreens.BottomNavViewModels

import android.util.Log
import com.m1zetzDev.swap.common.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.m1zetzDev.swap.auth.screens.auth5

const val CARDS_COLLECTION = "cards"

class HomeAddItemViewModel : ViewModel() {



    val fireBase = Firebase.firestore

    var stateAddItem by mutableStateOf(false)

    var messageName by mutableStateOf(TextField())
    var messageDescription by mutableStateOf(TextField())
    var messageCategory by mutableStateOf(TextField())

    var listOfCards by mutableStateOf(emptyList<Cards>())

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

    fun getData(){
        fireBase.collection(CARDS_COLLECTION).get().addOnCompleteListener{ task ->
            if(task.isSuccessful){
                listOfCards = task.result.toObjects(Cards::class.java)
            }
            else{
                Log.d("msg", "данные не получены")
            }
        }
    }

    fun getMyData(){
        val uid = Firebase.auth.currentUser?.uid?: return
        fireBase.collection(CARDS_COLLECTION).whereEqualTo("user_id", uid).get().addOnCompleteListener{ task ->
            if(task.isSuccessful){
                listOfCards = task.result.toObjects(Cards::class.java)
            }
            else{
                Log.d("msg", "данные не получены")
            }
        }
    }

    fun sendData() {
        val uid = Firebase.auth.currentUser?.uid?: return
        fireBase.collection(CARDS_COLLECTION).document().set(
            Cards(
                user_id = uid,
                name = messageName.value,
                description = messageDescription.value,
                category = messageCategory.value,
                imageUrl = ""
            )
        )
    }

}

data class Cards(
    val user_id: String = "",
    val name: String = "",
    val description: String = "",
    val category: String = "",
    val imageUrl: String = ""
)

sealed class FieldsState {
    class onChangeName(val name: String) : FieldsState()
    class onChangeDescription(val description: String) : FieldsState()
    class onChangeCategory(val category: String) : FieldsState()
}