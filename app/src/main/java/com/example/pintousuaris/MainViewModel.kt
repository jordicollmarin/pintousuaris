package com.example.pintousuaris

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.pintousuaris.data.UsuarisRepository
import com.example.pintousuaris.model.Post
import com.example.pintousuaris.model.Usuari
import kotlinx.coroutines.launch

/**
 * UI state for the MainActivity
 */
sealed interface PintoUsuarisUiState {
    data class Success(val usuaris: List<Usuari>) : PintoUsuarisUiState
    data object Error : PintoUsuarisUiState
    data object Loading : PintoUsuarisUiState
}

/**
 * UI state for the PostActivity
 */
sealed interface PintoPostsUiState {
    data class Success(val posts: List<Post>) : PintoPostsUiState
    data object Error : PintoPostsUiState
    data object Loading : PintoPostsUiState
}

class MainViewModel(
    private val repository: UsuarisRepository
) : ViewModel() {

    /** The mutable State that stores the status of the most recent request */
    var usuarisListUiState: PintoUsuarisUiState by mutableStateOf(PintoUsuarisUiState.Loading)
        private set

    /** The mutable State that stores the status of the most recent request */
    var postsListsUiState: PintoPostsUiState by mutableStateOf(PintoPostsUiState.Loading)
        private set

    /**
     * Call getUsuaris() on init so we can display status immediately.
     */
    init {
        getUsuaris()
    }


    /**
     * Gets Usuaris information from the API Retrofit service and updates the users list.
     */
    fun getUsuaris() {
        usuarisListUiState = PintoUsuarisUiState.Loading

        viewModelScope.launch {
            usuarisListUiState = try {
                val usuaris = repository.getUsuaris()
                Log.d("Pinto log USUARIS - SUCCESS", usuaris.toString())
                PintoUsuarisUiState.Success(usuaris)
            } catch (e: Exception) {
                Log.d("Pinto log USUARIS - ERROR", e.message.toString())
                PintoUsuarisUiState.Error
            }
        }
    }

    /**
     * Gets Posts from a selected Usuari using the API Retrofit service.
     */
    fun getPosts(userId: Int) {
        viewModelScope.launch {
            postsListsUiState = try {
                val posts = repository.getPosts(userId)
                Log.d("Pinto log POSTS - SUCCESS", posts.toString())
                PintoPostsUiState.Success(posts)
            } catch (e: Exception) {
                Log.d("Pinto log POSTS - ERROR", e.message.toString())
                PintoPostsUiState.Error
            }
        }
    }

    /**
     * Factory for [MainViewModel] that takes [UsuarisRepository] as a dependency
     */
    companion object {
        val creadorDeMainViewModel: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as PintoUsuarisApplication)
                val usuarisRepository = application.container.usuarisRepository
                MainViewModel(usuarisRepository)
            }
        }
    }
}