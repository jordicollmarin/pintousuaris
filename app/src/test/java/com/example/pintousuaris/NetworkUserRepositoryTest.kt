package com.example.pintousuaris

import com.example.pintousuaris.data.NetworkUsuarisRepository
import com.example.pintousuaris.fake.FakeDataSource
import com.example.pintousuaris.fake.FakeUserApiService
import com.example.pintousuaris.model.Usuari
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import com.example.pintousuaris.data.UsuarisRepository

class NetworkUserRepositoryTest {

    @Test
    fun networkUsersRepository_getUsuaris_verifyUserList() =
        runTest {
            val repository = NetworkUsuarisRepository(
                usuarisService = FakeUserApiService()
            )
            assertEquals(FakeDataSource.usersList, repository.getUsuaris())
            assertEquals(FakeDataSource.postsList, repository.getPosts(userId))

        }
}