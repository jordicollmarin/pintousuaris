package com.example.pintousuaris.data

import com.example.pintousuaris.model.Post
import com.example.pintousuaris.model.Usuari
import com.example.pintousuaris.network.UsuarisService

/**
 * Repository that fetches list of Usuaris and Posts from a Usuari.
 */
interface UsuarisRepository {
    /** Fetches list of usuaris from API */
    suspend fun getUsuaris(): List<Usuari>

    /** Fetches posts from a user from API */
    suspend fun getPosts(userId: Int): List<Post>
}

/**
 * Network Implementation of Repository that fetches a list of Usuaris and Posts from a Usuari.
 */
class NetworkUsuarisRepository(
    private val usuarisService: UsuarisService
) : UsuarisRepository {
    /** Fetches list of Usuaris from API */
    override suspend fun getUsuaris(): List<Usuari> = usuarisService.getUsuaris()

    /** Fetches posts from a user from API */
    override suspend fun getPosts(userId: Int): List<Post> = usuarisService.getPosts(userId)
}