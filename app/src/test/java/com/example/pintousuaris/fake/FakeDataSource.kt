package com.example.pintousuaris.fake

import com.example.pintousuaris.model.Post
import com.example.pintousuaris.model.Usuari

object FakeDataSource {

    private const val idOne = 666
    private const val idTwo = 999
    private const val nameOne = "Juan"
    private const val nameTwo = "Pako"
    private const val emailOne = "url.one"
    private const val emailTwo = "url.two"

    private const val idPostOne = 1111
    private const val titlePostOne = "Post1 Fake title"
    private const val bodyPostOne = "Post1 Fake body"
    const val idPostTwo = 2222
    const val titlePostTwo = "Post2 Fake title"
    const val bodyPostTwo = "Post2 Fake body"
    const val idPostThree = 3333
    const val titlePostThree = "Post3 Fake title"
    const val bodyPostThree = "Post3 Fake body"

    val usersList = listOf(
        Usuari(
            id = idOne,
            name = nameOne,
            email = emailOne
        ),
        Usuari(
            id = idTwo,
            name = nameTwo,
            email = emailTwo
        )
    )

    val postsList = listOf(
        Post(
            userId = idOne,
            id = idPostOne,
            title = titlePostOne,
            body = bodyPostOne
        ),
        Post(
            userId = idTwo,
            id = idPostTwo,
            title = titlePostTwo,
            body = bodyPostTwo
        ),
        Post(
            userId = idTwo,
            id = idPostThree,
            title = titlePostThree,
            body = bodyPostThree
    )
    )
}


