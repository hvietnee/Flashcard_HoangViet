package com.hoangviet.flashcard

import androidx.lifecycle.*
import kotlinx.coroutines.launch

class FlashcardViewModel(private val repository: FlashcardRepository) : ViewModel() {
    val allFlashcards: LiveData<List<Flashcard>> = repository.allFlashcards.asLiveData()

    fun insert(flashcard: Flashcard) = viewModelScope.launch {
        repository.insert(flashcard)
    }
}

class FlashcardViewModelFactory(private val repository: FlashcardRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FlashcardViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FlashcardViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}