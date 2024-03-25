package ru.zinoview.entityrelationsapp.ui.songs

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
fun SongsScreen(
    albId: Long,
    viewModel: MusicAppViewModel,
    navController: NavController
) {
    viewModel.loadAlbumSongs(albId)

    val albumSongs = viewModel.albumSongs.collectAsState().value

    Column(
        modifier = Modifier.padding(all = 20.dp),
    ) {
        var albumId by remember {
            mutableStateOf(albId.toString())
        }
        var songTitle by remember {
            mutableStateOf("")
        }

        TextField(
            value = albumId,
            onValueChange = {
                albumId = it
            },
            placeholder = { Text("Album id") }
        )

        TextField(
            value = songTitle,
            onValueChange = {
                songTitle = it
            },
            placeholder = { Text("Song title") }
        )
        TextButton(
            onClick = {
                viewModel.addAlbumSong(
                    albumId = albumId.toLong(), // todo validate 'cause can be string
                    songTitle = songTitle
                )
                songTitle = ""
            },
        ) {
            Text(text = "Add")
        }

        when {
            albumSongs != null && albumSongs.songs.isNotEmpty() -> {
                LazyColumn {
                    itemsIndexed(albumSongs.songs) { _, song ->
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
                                            route = "${MusicAppScreen.SongAlbums.name}/${song.songId}"
                                        )
                                    },
                                text = String.format(
                                    "title: %s, songId: %d, albumId: %d",
                                    song.title,
                                    song.songId,
                                    albumSongs.album.albumId
                                ),
                            )
                        }
                    }
                }
            }

            else -> {
                Text(text = "No albums by albumId: $albId")
            }
        }
    }
}