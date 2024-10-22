package com.example.pintousuaris.ui

import androidx.compose.foundation.clickable
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.example.pintousuaris.R
import com.example.pintousuaris.UsuarisUiState
import com.example.pintousuaris.model.Usuari

@Composable
fun UsuarisScreen(
    usuarisUiState: UsuarisUiState,
    onItemClicked: ((Int, String) -> Unit),
    onErrorRetry: () -> Unit
) {
    when (usuarisUiState) {
        is UsuarisUiState.Loading -> Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
        }

        is UsuarisUiState.Success -> UsuarisList(usuarisUiState.usuaris, onItemClicked)

        is UsuarisUiState.Error -> Column(
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
fun UsuarisList(
    usuaris: List<Usuari>,
    onItemClicked: ((Int, String) -> Unit)
) {
    LazyColumn {
        items(usuaris) { usuari ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clickable { onItemClicked(usuari.id, usuari.name) }
            ) {
                Text(
                    text = usuari.name,
                    fontSize = TextUnit(20f, TextUnitType.Sp),
                    fontWeight = FontWeight.Bold
                )
                Text(usuari.email)
                HorizontalDivider()
            }
        }
    }
}