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

    fun reject(exchange: Exchanges) {

        val myIdOther = exchange.userIdOther
        val nameOther = exchange.nameOther
        val descriptionOther = exchange.descriptionOther
        val categoryOther = exchange.categoryOther

        val acceptedId = exchange.acceptedId
        val acceptedName = exchange.acceptedName
        val acceptedDescription = exchange.acceptedDescription
        val acceptedCategory = exchange.acceptedCategory

        Firebase.firestore.collection("cardstoexchange")
            .whereEqualTo("userIdOther", myIdOther)
            .whereEqualTo("nameOther", nameOther)
            .whereEqualTo("descriptionOther", descriptionOther)
            .whereEqualTo("categoryOther", categoryOther)

            .whereEqualTo("acceptedId", acceptedId)
            .whereEqualTo("acceptedName", acceptedName)
            .whereEqualTo("acceptedDescription", acceptedDescription)
            .whereEqualTo("acceptedCategory", acceptedCategory)
            .get()
            .addOnSuccessListener { querySnapshot ->
                if (!querySnapshot.isEmpty) {
                    val batch = Firebase.firestore.batch()
                    for (document in querySnapshot.documents) {
                        batch.delete(document.reference)
                    }
                    batch.commit()
                } else {
                    println("Документы, соответствующие запросу, не найдены.")
                }
            }
            .addOnFailureListener { e ->
                println("Ошибка при выполнении запроса: $e")
            }
    }

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
        val myIdOther = exchange.userIdOther
        val nameOther = exchange.nameOther
        val descriptionOther = exchange.descriptionOther
        val categoryOther = exchange.categoryOther

        val acceptedId = exchange.acceptedId
        val acceptedName = exchange.acceptedName
        val acceptedDescription = exchange.acceptedDescription
        val acceptedCategory = exchange.acceptedCategory

        Firebase.firestore.collection("successfulExchanges")
            .whereEqualTo("acceptedId", acceptedId)
            .whereEqualTo("acceptedName", acceptedName)
            .whereEqualTo("acceptedDescription", acceptedDescription)
            .whereEqualTo("acceptedCategory", acceptedCategory)
            .whereEqualTo("userIdOther", myIdOther)
            .whereEqualTo("nameOther", nameOther)
            .whereEqualTo("descriptionOther", descriptionOther)
            .whereEqualTo("categoryOther", categoryOther)
            .get()
            .addOnSuccessListener { querySnapshot ->
                if (querySnapshot.isEmpty) {

                    Firebase.firestore.collection("successfulExchanges")
                        .add(exchange)
                        .addOnSuccessListener {
                            deleteAllCardFromExchanges(
                                myIdOther,
                                nameOther,
                                descriptionOther,
                                categoryOther,
                                acceptedId,
                                acceptedName,
                                acceptedDescription,
                                acceptedCategory
                            )

                        }
                        .addOnFailureListener {
                            Log.e(
                                "Firebase",
                                "Ошибка при сохранении обмена в successfulExchanges",
                                it
                            )
                        }


                } else {

                    deleteAllCardFromExchanges(
                        myIdOther,
                        nameOther,
                        descriptionOther,
                        categoryOther,
                        acceptedId,
                        acceptedName,
                        acceptedDescription,
                        acceptedCategory
                    )
                }
            }
    }

    fun deleteAllCardFromExchanges(
        myIdOther: String,
        nameOther: String,
        descriptionOther: String,
        categoryOther: String,
        acceptedId: String,
        acceptedName: String,
        acceptedDescription: String,
        acceptedCategory: String
    ) {
        Firebase.firestore.collection("cards")
            .whereEqualTo("user_id", myIdOther)
            .whereEqualTo("name", nameOther)
            .whereEqualTo("description", descriptionOther)
            .whereEqualTo("category", categoryOther)
            .get()
            .addOnSuccessListener { querySnapshot ->
                if (!querySnapshot.isEmpty) {
                    val batch = Firebase.firestore.batch()
                    for (document in querySnapshot.documents) {
                        batch.delete(document.reference)
                    }
                    batch.commit()
                } else {
                    println("Документы, соответствующие запросу, не найдены.")
                }
            }
            .addOnFailureListener { e ->
                println("Ошибка при выполнении запроса: $e")
            }

        Firebase.firestore.collection("cards")
            .whereEqualTo("user_id", acceptedId)
            .whereEqualTo("name", acceptedName)
            .whereEqualTo("description", acceptedDescription)
            .whereEqualTo("category", acceptedCategory)
            .get()
            .addOnSuccessListener { querySnapshot ->
                if (!querySnapshot.isEmpty) {
                    val batch = Firebase.firestore.batch()
                    for (document in querySnapshot.documents) {
                        batch.delete(document.reference)
                    }
                    batch.commit()
                } else {
                    println("Документы, соответствующие запросу, не найдены.")
                }
            }
            .addOnFailureListener { e ->
                println("Ошибка при выполнении запроса: $e")
            }
        Firebase.firestore.collection(CARDS_IN_EXCHANGE)
            .whereEqualTo("userIdOther", myIdOther)
            .whereEqualTo("nameOther", nameOther)
            .whereEqualTo("descriptionOther", descriptionOther)
            .whereEqualTo("categoryOther", categoryOther)
            .whereEqualTo("acceptedId", acceptedId)
            .whereEqualTo("acceptedName", acceptedName)
            .whereEqualTo("acceptedDescription", acceptedDescription)
            .whereEqualTo("acceptedCategory", acceptedCategory)
            .get()
            .addOnSuccessListener { querySnapshot ->
                if (!querySnapshot.isEmpty) {
                    val batch = Firebase.firestore.batch()
                    for (document in querySnapshot.documents) {
                        batch.delete(document.reference)
                    }
                    batch.commit()
                    Log.d("Firebase", "Обмен успешно удален из cardstoexchange после принятия.")
                } else {
                    println("Обмен не найден в cardstoexchange для удаления.")
                }
            }
            .addOnFailureListener { e ->
                Log.e("Firebase", "Ошибка при удалении обмена из cardstoexchange: $e")
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