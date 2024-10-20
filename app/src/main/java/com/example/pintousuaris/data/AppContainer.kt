package com.example.pintousuaris.data

import com.example.pintousuaris.network.UsuarisService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface AppContainer {
    val usuarisRepository: UsuarisRepository
}

/**
 * Implementation for the Dependency Injection container at the application level.
 *
 * Variables are initialized lazily and the same instance is shared across the whole app.
 */
class DefaultAppContainer : AppContainer {
    private val baseUrl = "https://jsonplaceholder.typicode.com/"

    /**
     * Use the Retrofit builder to build a retrofit object using a kotlinx.serialization converter
     */
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(baseUrl)
        .build()

    /**
     * Retrofit service object for creating api calls
     */
    private val retrofitService: UsuarisService by lazy {
        retrofit.create(UsuarisService::class.java)
    }

    override val usuarisRepository: UsuarisRepository by lazy {
        NetworkUsuarisRepository(retrofitService)
    }
}