package com.example.pintousuaris.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.pintousuaris.R
import com.example.pintousuaris.UiState
import com.example.pintousuaris.model.Post

@Composable
fun PostsScreen(
    uiState: UiState<Post>,
    onErrorRetry: () -> Unit
) {
    when (uiState) {
        is UiState.Loading -> Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
        }

        is UiState.Success -> PostsList(uiState.list)

        is UiState.Error -> Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = stringResource(id = R.string.error))
            Button(onClick = onErrorRetry) {
                Text(text = stringResource(id = R.string.retry))
            }
        }
    }
}

@Composable
fun PostsList(list: List<Post>) {
    LazyColumn {
        items(list) { post ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(post.title)
                Text(post.body)
                HorizontalDivider()
            }
        }
    }
}