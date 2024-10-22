package com.example.pintousuaris

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pintousuaris.model.Post
import com.example.pintousuaris.model.Usuari
import com.example.pintousuaris.network.UsuarisService
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * UI state for the MainActivity
 */
sealed interface UsuarisUiState {
    data class Success(val usuaris: List<Usuari>) : UsuarisUiState
    data object Error : UsuarisUiState
    data object Loading : UsuarisUiState
}

sealed interface PostsUiState {
    data class Success(val posts: List<Post>) : PostsUiState
    data object Error : PostsUiState
    data object Loading : PostsUiState
}

class MainViewModel : ViewModel() {

    private val baseUrl = "https://jsonplaceholder.typicode.com/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(baseUrl)
        .build()

    private val retrofitService: UsuarisService by lazy {
        retrofit.create(UsuarisService::class.java)
    }

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
                val usuaris = retrofitService.getUsuaris()
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
    fun getPosts(userId: Int) {
        _postsListUiState.value = PostsUiState.Loading

        viewModelScope.launch {
            _postsListUiState.value = try {
                val posts = retrofitService.getPosts(userId)
                Log.d("Pinto log POSTS - SUCCESS", posts.toString())
                PostsUiState.Success(posts)
            } catch (e: Exception) {
                Log.d("Pinto log POSTS - ERROR", e.message.toString())
                PostsUiState.Error
            }
        }
    }
}