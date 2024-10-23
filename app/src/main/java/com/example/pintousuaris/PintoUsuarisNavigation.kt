package com.example.pintousuaris

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.pintousuaris.ui.PostsScreen
import com.example.pintousuaris.ui.UsuarisScreen

enum class PintoUsuarisNavigation(@StringRes val title: Int) {
    Usuaris(title = R.string.usuaris_screen_title),
    Posts(title = R.string.posts_screen_title),
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun PintoUsuarisTopBar(
    currentScreen: PintoUsuarisNavigation,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(currentScreen.title),
                color = Color.Yellow
            )
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = Color.Blue
        ),
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = stringResource(R.string.back_button_content_description),
                        tint = Color.Yellow
                    )
                }
            }
        }
    )
}

@Composable
fun PintoUsuarisApp(
    viewModel: MainViewModel = viewModel<MainViewModel>(),
    navController: NavHostController = rememberNavController()
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = PintoUsuarisNavigation.valueOf(
        value = backStackEntry?.destination?.route ?: PintoUsuarisNavigation.Usuaris.name
    )

    Scaffold(
        modifier = Modifier,
        topBar = {
            PintoUsuarisTopBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        }
    ) { paddingValues ->
        val usuarisUiState = viewModel.usuarisUiState.observeAsState()
        val uiStatePosts = viewModel.postsListUiState.observeAsState()

        NavHost(
            navController = navController,
            startDestination = PintoUsuarisNavigation.Usuaris.name,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(route = PintoUsuarisNavigation.Usuaris.name) {
                UsuarisScreen(
                    usuarisUiState = usuarisUiState.value ?: UsuarisUiState.Error,
                   // onItemClicked = { userId ->
                    //    viewModel.getPosts(userId)
                    onItemClicked = { userId, userName ->
                            viewModel.getPosts(userId, userName)
                        navController.navigate(PintoUsuarisNavigation.Posts.name)
                    },
                    onErrorRetry = { viewModel.getUsuaris() }
                )
            }

            composable(route = PintoUsuarisNavigation.Posts.name) {
                PostsScreen(
                    uiState = uiStatePosts.value ?: PostsUiState.Error,
                    onErrorRetry = {
                        navController.navigateUp()
                    }
                )
            }
        }
    }
}