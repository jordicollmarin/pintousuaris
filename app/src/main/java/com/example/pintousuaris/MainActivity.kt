package com.example.pintousuaris

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pintousuaris.PostActivity.Companion.POST_ID
import com.example.pintousuaris.ui.theme.PintoUsuarisTheme

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PintoUsuarisTheme {
                val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
                Scaffold(
                    modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                    topBar = {
                        CenterAlignedTopAppBar(
                            scrollBehavior = scrollBehavior,
                            title = {
                                Text(
                                    text = stringResource(R.string.app_name),
                                    style = MaterialTheme.typography.headlineSmall,
                                )
                            }
                        )
                    }
                ) { padding ->
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding)
                    )
                    {
                        val viewModel: MainViewModel = viewModel(
                            factory = MainViewModel.creadorDeMainViewModel
                        )

                        val uiState = viewModel.usuarisListUiState

                        when (uiState) {
                            is PintoUsuarisUiState.Loading -> Text(
                                modifier = Modifier.fillMaxSize(),
                                text = getString(R.string.loading)
                            )

                            is PintoUsuarisUiState.Success -> LazyColumn {
                                itemsIndexed(uiState.usuaris) { _, usuari ->
                                    Column(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .padding(8.dp)
                                            .clickable {
                                                // Replace amb Compose navigation
                                                this@MainActivity.startActivity(
                                                    Intent(
                                                        this@MainActivity,
                                                        PostActivity::class.java
                                                    ).apply {
                                                        putExtra(POST_ID, usuari.id)
                                                    }
                                                )
                                            }
                                    ) {
                                        Text(
                                            modifier = Modifier.fillMaxSize(),
                                            color = Color.Red,
                                            text = "Nom: ${usuari.name}"
                                        )
                                        Text(
                                            modifier = Modifier.fillMaxSize(),
                                            text = "Email: ${usuari.email}"
                                        )
                                        Text(
                                            modifier = Modifier.fillMaxSize(),
                                            text = "Teleèfon: ${usuari.phone}"
                                        )
                                    }

                                }
                            }

                            is PintoUsuarisUiState.Error -> {
                                Column(modifier = Modifier.fillMaxSize()) {
                                    Text(text = getString(R.string.error))
                                    Button(onClick = { viewModel.getUsuaris() }) {
                                        Text(text = getString(R.string.retry))
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}