package com.example.pintousuaris.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.example.pintousuaris.PostsUiState
import com.example.pintousuaris.R

@Composable
fun PostsScreen(
    uiState: PostsUiState,
    onErrorRetry: () -> Unit
) {
    when (uiState) {
        is PostsUiState.Loading -> Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
        }

        is PostsUiState.Success -> PostsList(uiState)

        is PostsUiState.Error -> Column(
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
fun PostsList(data: PostsUiState.Success) {
    Column {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            text = "Usuari seleccionat: ${data.userName}",
            color = Color.Blue,
            fontSize = TextUnit(16f, TextUnitType.Sp),
            fontWeight = FontWeight.Bold
        )

        LazyColumn {
            items(data.posts) { post ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Text(
                        text = post.title,
                        fontSize = TextUnit(20f, TextUnitType.Sp),
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = post.body,
                        fontSize = TextUnit(12f, TextUnitType.Sp),
                    )
                    HorizontalDivider()
                }
            }
        }
    }
}