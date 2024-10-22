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
sealed interface UiState<out R> {
    data class Success<T>(val list: List<T>) : UiState<T>
    data object Error : UiState<Nothing>
    data object Loading : UiState<Nothing>
}

class MainViewModel(
    private val repository: UsuarisRepository
) : ViewModel() {

    /** The mutable State that stores the status of the user request */
    private val _usuarisListUiState = MutableLiveData<UiState<Usuari>>(UiState.Loading)
    val usuarisListUiState = _usuarisListUiState

    /** The mutable State that stores the status of the posts request */
    private val _postsListUiState = MutableLiveData<UiState<Post>>(UiState.Loading)
    val postsListUiState = _postsListUiState

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
        _usuarisListUiState.value = UiState.Loading

        viewModelScope.launch {
            _usuarisListUiState.value = try {
                val usuaris = repository.getUsuaris()
                Log.d("Pinto log USUARIS - SUCCESS", usuaris.toString())
                UiState.Success(usuaris)
            } catch (e: Exception) {
                Log.d("Pinto log USUARIS - ERROR", e.message.toString())
                UiState.Error
            }
        }
    }

    /**
     * Gets Posts from a user
     */
    fun getPosts(userId: Int) {
        _postsListUiState.value = UiState.Loading

        viewModelScope.launch {
            _postsListUiState.value = try {
                val posts = repository.getPosts(userId)
                Log.d("Pinto log POSTS - SUCCESS", posts.toString())
                UiState.Success(posts)
            } catch (e: Exception) {
                Log.d("Pinto log POSTS - ERROR", e.message.toString())
                UiState.Error
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