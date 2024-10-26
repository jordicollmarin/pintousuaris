package com.example.pintousuaris

import com.example.pintousuaris.data.NetworkUsuarisRepository
import com.example.pintousuaris.fake.FakeDataSource
import com.example.pintousuaris.fake.FakeDataSource.bodyPostTwo
import com.example.pintousuaris.fake.FakeDataSource.idPostTwo
import com.example.pintousuaris.fake.FakeDataSource.titlePostTwo
import com.example.pintousuaris.fake.FakeUserApiService
import com.example.pintousuaris.model.Post
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class NetworkUserRepositoryTest {

    private val repository = NetworkUsuarisRepository(
        usuarisService = FakeUserApiService()
    )

    @Test
    fun networkUsersRepository_getUsuaris_verifyUserList() = runTest {
        assertEquals(FakeDataSource.usersList, repository.getUsuaris())
    }

    @Test
    fun networkUsersRepository_getPosts_verifyPostsWhenUserHas1Post() = runTest {
        val userId = 999

        val fakeList = listOf(
            Post(
                userId = userId,
                id = idPostTwo,
                title = titlePostTwo,
                body = bodyPostTwo
            )
        )
        val repositoryList = repository.getPosts(userId)

        assertEquals(fakeList, repositoryList)
    }

    @Test
    fun networkUsersRepository_getPosts_verifyPostsWhenUserHas2Posts() = runTest {

    }
}