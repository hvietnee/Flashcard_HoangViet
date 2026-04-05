package com.hoangviet.flashcard

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import androidx.lifecycle.asLiveData

class FlashcardViewModel(private val dao: FlashcardDao) : ViewModel() {

    val allFlashcards: LiveData<List<Flashcard>> = dao.getAllFlashcards().asLiveData()

    fun insert(flashcard: Flashcard) = viewModelScope.launch {
        dao.insert(flashcard)
    }

    fun delete(flashcard: Flashcard) = viewModelScope.launch {
        dao.delete(flashcard)
    }
}

class FlashcardViewModelFactory(private val dao: FlashcardDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FlashcardViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FlashcardViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}