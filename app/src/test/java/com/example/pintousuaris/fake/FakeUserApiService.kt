package com.example.pintousuaris.fake

import com.example.pintousuaris.model.Post
import com.example.pintousuaris.model.Usuari
import com.example.pintousuaris.network.UsuarisService

class FakeUserApiService : UsuarisService {
    override suspend fun getUsuaris(): List<Usuari> {
        return FakeDataSource.usersList
    }

    override suspend fun getPosts(userId: Int): List<Post> {
        return FakeDataSource.postsList.filter { post ->
            post.userId == userId
        }
    }
}