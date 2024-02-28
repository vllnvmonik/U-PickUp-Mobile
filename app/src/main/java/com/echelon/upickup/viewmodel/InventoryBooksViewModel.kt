package com.echelon.upickup.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.echelon.upickup.network.apimodel.Books
import com.echelon.upickup.network.apimodel.BooksResponse
import com.echelon.upickup.repository.InventoryBooksRepository
import com.echelon.upickup.sharedprefs.BooksManager
import kotlinx.coroutines.launch

class InventoryBooksViewModel: ViewModel() {
    private val inventoryBooksRepository = InventoryBooksRepository()

    private val _books = MutableLiveData<List<BooksResponse>>()
    val books: LiveData<List<BooksResponse>> = _books

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun fetchBooks() {
        viewModelScope.launch {
            try {
                val response = inventoryBooksRepository.getBooks()
                if (response.isSuccessful){
                    val details = response.body()
                    details?.let {
                        _books.value = listOf(it)
                        Log.d("InventoryBooksViewModel", "Fetched books: $details")
                    }
                    BooksManager.saveBooksResponse(details)
                } else {
                    Log.e("InventoryBooksViewModel", "Failed to fetch books: ${response.code()}")
                    // Handle error response
                }
            } catch (e: Exception) {
                Log.e("InventoryBooksViewModel", "Error fetching books: ${e.message}", e)
                // Handle network exceptions
            }
        }
    }
}
