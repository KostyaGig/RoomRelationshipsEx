package ru.zinoview.entityrelationsapp.ui.albums

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ru.zinoview.entityrelationsapp.MusicAppScreen
import ru.zinoview.entityrelationsapp.MusicAppViewModel

@Composable
fun AlbumsScreen(
    userId: Long,
    viewModel: MusicAppViewModel,
    navController: NavController
) {

    viewModel.loadAlbums(userId)
    val userAlbums = viewModel.albums.collectAsState().value

    Column(
        modifier = Modifier.padding(all = 20.dp),
    ) {
        var albumTitle by remember {
            mutableStateOf("")
        }

        TextField(
            value = albumTitle,
            onValueChange = {
                albumTitle = it
            },
            placeholder = { Text("Album title") }
        )
        TextButton(
            onClick = {
                viewModel.addAlbum(
                    userId = userId,
                    albumTitle = albumTitle
                )
                albumTitle = ""
            },
        ) {
            Text(text = "Add")
        }

        when {
            userAlbums != null && userAlbums.albums.isNotEmpty() -> {
                LazyColumn {
                    itemsIndexed(userAlbums.albums) { _, album ->
                        Card(
                            modifier = Modifier.padding(all = 20.dp),
                            shape = CircleShape,
                            border = BorderStroke(2.dp, Color.Gray),
                        ) {
                            Text(
                                modifier = Modifier
                                    .padding(all = 20.dp)
                                    .clickable {
                                        navController.navigate(
                                            route = "${MusicAppScreen.Songs.name}/${album.albumId}",
                                        )
                                    },
                                text = String.format(
                                    "title: %s, id: %d, userOwnerId: %d",
                                    album.title,
                                    album.albumId,
                                    album.userIdOwner
                                ),
                            )
                        }
                    }
                }
            }

            else -> {
                Text(text = "No albums by userId: $userId")
            }
        }
    }
}