package com.example.pintousuaris

import android.util.Log
import androidx.lifecycle.MutableLiveData
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
sealed interface UsuarisUiState {
    data class Success(val usuaris: List<Usuari>) : UsuarisUiState
    data object Error : UsuarisUiState
    data object Loading : UsuarisUiState
}

sealed interface PostsUiState {
    data class Success(val userName: String, val posts: List<Post>) : PostsUiState
    data object Error : PostsUiState
    data object Loading : PostsUiState
}

class MainViewModel(
    private val usuarisRepository: UsuarisRepository
) : ViewModel() {

    /** The mutable State that stores the status of the user request */
    private val _usuarisUiState = MutableLiveData<UsuarisUiState>(UsuarisUiState.Loading)
    val usuarisUiState = _usuarisUiState

    /** The mutable State that stores the status of the posts request */
    private val _postsListUiState = MutableLiveData<PostsUiState>(PostsUiState.Loading)
    val postsListUiState = _postsListUiState

    /**
     * Call getUsuaris() on init so we can display status immediately.
     */
    init {
        getUsuaris()
    }

    /**
     * Gets Usuaris using the API Retrofit Service (a través del [UsuarisRepository])
     */
    fun getUsuaris() {
        _usuarisUiState.value = UsuarisUiState.Loading

        viewModelScope.launch {
            _usuarisUiState.value = try {
                val usuaris = usuarisRepository.getUsuaris()
                Log.d("Pinto log USUARIS - SUCCESS", usuaris.toString())
                UsuarisUiState.Success(usuaris)
            } catch (e: Exception) {
                Log.d("Pinto log USUARIS - ERROR", e.message.toString())
                UsuarisUiState.Error
            }
        }
    }

    /**
     * Gets Posts from a specific user using the API Retrofit Service (a través del [UsuarisRepository])
     */
    fun getPosts(userId: Int, userName: String) {
        _postsListUiState.value = PostsUiState.Loading

        viewModelScope.launch {
            _postsListUiState.value = try {
                val posts = usuarisRepository.getPosts(userId)
                Log.d("Pinto log POSTS - SUCCESS", posts.toString())
                PostsUiState.Success(userName, posts)
            } catch (e: Exception) {
                Log.d("Pinto log POSTS - ERROR", e.message.toString())
                PostsUiState.Error
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