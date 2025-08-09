package com.example.flashcard_quiz

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CardViewModel(private val dao: cardDao) : ViewModel() {

    var question = ""
    var answer = ""
    var id = -1L


    private var cardList: List<Cardtable> = listOf()
    private var currentIndex = 0


    private val _currentCard = MutableLiveData<Cardtable?>()
    val currentCard: LiveData<Cardtable?> get() = _currentCard

    fun loadCardsForCategory(categoryId: Long) {
        id = categoryId
        viewModelScope.launch(Dispatchers.IO) {
            cardList = dao.getCardsByCategory(id)
            if (cardList.isNotEmpty()) {
                currentIndex = 0
                _currentCard.postValue(cardList[currentIndex])
            } else {
                _currentCard.postValue(null)
            }
        }

    }

    fun nextCard() {
        if (cardList.isNotEmpty()) {
            currentIndex = (currentIndex + 1) % cardList.size
            _currentCard.postValue(cardList[currentIndex])

        }
    }
    fun getCardById(cardId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val card = dao.getCardById(cardId)
            _currentCard.postValue(card)
        }
    }


    fun prevCard() {
        if (cardList.isNotEmpty()) {
            currentIndex = if (currentIndex - 1 < 0) cardList.size - 1 else currentIndex - 1
            _currentCard.postValue(cardList[currentIndex])

        }
    }

    fun addCard(question: String, answer: String) {
        if (id <= 0L) {
            return}
        val newCard = Cardtable(
            question = question,
            answer = answer,
            categoryId = id
        )
        viewModelScope.launch(Dispatchers.IO) {
            dao.insert(newCard)
            val allCards = dao.getall()

            loadCardsForCategory(id)
        }

    }

    fun deleteCurrentCard() {
        val card = _currentCard.value ?: return
        viewModelScope.launch(Dispatchers.IO) {
            dao.delete(card)
            loadCardsForCategory(id)
        }
    }

    fun updateCurrentCard(updatedQuestion: String, updatedAnswer: String) {
        val card = _currentCard.value ?: return
        val updatedCard = card.copy(
            question = updatedQuestion,
            answer = updatedAnswer
        )
        viewModelScope.launch(Dispatchers.IO) {
            dao.update(updatedCard)
            loadCardsForCategory(id)
        }
    }

}
