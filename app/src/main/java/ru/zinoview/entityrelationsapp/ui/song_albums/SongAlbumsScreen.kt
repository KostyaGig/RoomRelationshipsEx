package ru.zinoview.entityrelationsapp.ui.song_albums

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
fun SongAlbumsScreen(
    sngId: Long,
    viewModel: MusicAppViewModel,
    navController: NavController
) {
    viewModel.loadSongAlbums(sngId)

    val songAlbums = viewModel.songAlbums.collectAsState().value

    Column(
        modifier = Modifier.padding(all = 20.dp),
    ) {
        var albumId by remember {
            mutableStateOf("")
        }

        var songId by remember {
            mutableStateOf(sngId.toString())
        }

        TextField(
            value = albumId,
            onValueChange = {
                albumId = it
            },
            placeholder = { Text("Album id") }
        )

        TextField(
            value = songId,
            onValueChange = {
                songId = it
            },
            placeholder = { Text("Song id") }
        )
        TextButton(
            onClick = {
                viewModel.addSongToAlbum(
                    albumId = albumId.toLong(), // todo validate 'cause can be string
                    songId = songId.toLong(), // todo validate 'cause can be string
                )
                albumId = ""
            },
        ) {
            Text(text = "Add")
        }

        when {
            songAlbums != null && songAlbums.albums.isNotEmpty() -> {
                LazyColumn {
                    itemsIndexed(songAlbums.albums) { _, album ->
                        Card(
                            modifier = Modifier.padding(all = 20.dp),
                            shape = CircleShape,
                            border = BorderStroke(2.dp, Color.Gray),
                        ) {
                            Text(
                                modifier = Modifier
                                    .padding(all = 20.dp)
                                    .clickable {

                                    },
                                text = String.format(
                                    "songTitle: %s, albumTitle: %s, songId: %d, albumId: %d",
                                    songAlbums.song.title,
                                    album.title,
                                    songAlbums.song.songId,
                                    album.albumId
                                ),
                            )
                        }
                    }
                }
            }

            else -> {
                Text(text = "No albums by songId: $sngId")
            }
        }
    }
}