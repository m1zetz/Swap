package com.m1zetzDev.swap.mainAppButNav.MainScreens.BottomNavViewModels

import com.m1zetzDev.swap.common.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class HomeAddItemViewModel : ViewModel() {

    val fireBase = Firebase.firestore

    var stateAddItem by mutableStateOf(false)

    var messageName by mutableStateOf(TextField())
    var messageDescription by mutableStateOf(TextField())
    var messageCategory by mutableStateOf(TextField())


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

    fun sendData(){
        fireBase.collection("cards").document().set("name" to messageName)
        fireBase.collection("cards").document().set("description" to messageDescription)
        fireBase.collection("cards").document().set("category" to messageCategory)
    }

}

sealed class FieldsState {
    class onChangeName(val name: String) : FieldsState()
    class onChangeDescription(val description: String) : FieldsState()
    class onChangeCategory(val category: String) : FieldsState()
}