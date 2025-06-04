package com.m1zetzDev.swap.mainAppButNav.MainScreens.BottomNavViewModels

import com.m1zetzDev.swap.common.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel


class HomeAddItemViewModel : ViewModel() {

    var stateAddItem by mutableStateOf(false)

    var messageName by mutableStateOf(TextField())
    var messageDescription by mutableStateOf(TextField())


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

        }
    }
}

sealed class FieldsState {
    class onChangeName(val name: String) : FieldsState()
    class onChangeDescription(val description: String) : FieldsState()
}