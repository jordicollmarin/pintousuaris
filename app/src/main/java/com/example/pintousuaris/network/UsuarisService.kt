package com.example.pintousuaris.network

import com.example.pintousuaris.model.Post
import com.example.pintousuaris.model.Usuari
import retrofit2.http.GET
import retrofit2.http.Query

interface UsuarisService {

    @GET("users")
    suspend fun getUsuaris(): List<Usuari>

    @GET("posts")
    suspend fun getPosts(
        @Query("userId") userId: Int,
    ): List<Post>
}