package ru.zinoview.entityrelationsapp

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ru.zinoview.entityrelationsapp.ui.albums.AlbumsScreen
import ru.zinoview.entityrelationsapp.ui.song_albums.SongAlbumsScreen
import ru.zinoview.entityrelationsapp.ui.songs.SongsScreen
import ru.zinoview.entityrelationsapp.ui.users.UsersScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MusicAppBar(
    title: String,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(title) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
        }
    )
}

@Composable
fun MusicApp(
    viewModel: MusicAppViewModel,
    navController: NavHostController = rememberNavController()
) {

    // Get current back stack entry
    val backStackEntry by navController.currentBackStackEntryAsState()

    val currentScreen = MusicAppScreen.entries.firstOrNull {
        backStackEntry?.destination?.route?.contains(it.name) ?: false
    } ?: MusicAppScreen.Users

    Scaffold(
        topBar = {
            MusicAppBar(
                title = currentScreen.name,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        }
    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = MusicAppScreen.Users.name,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            composable(route = MusicAppScreen.Users.name) {
                UsersScreen(
                    navController = navController,
                    viewModel = viewModel
                )
            }

            composable(
                route = "${MusicAppScreen.Albums.name}/{userId}",
                arguments = listOf(navArgument("userId") { type = NavType.LongType })
            ) {
                AlbumsScreen(
                    userId = backStackEntry?.arguments?.getLong("userId") ?: -1,
                    navController = navController,
                    viewModel = viewModel
                )
            }

            composable(
                route = "${MusicAppScreen.Songs.name}/{albumId}",
                arguments = listOf(navArgument("albumId") { type = NavType.LongType })
            ) {
                SongsScreen(
                    albId = backStackEntry?.arguments?.getLong("albumId") ?: -1,
                    navController = navController,
                    viewModel = viewModel
                )
            }

            composable(
                route = "${MusicAppScreen.SongAlbums.name}/{songId}",
                arguments = listOf(navArgument("songId") { type = NavType.LongType })
            ) {
                SongAlbumsScreen(
                    sngId = backStackEntry?.arguments?.getLong("songId") ?: -1,
                    navController = navController,
                    viewModel = viewModel
                )
            }
        }
    }
}

enum class MusicAppScreen {
    Users,
    Albums,
    Songs,
    SongAlbums
}