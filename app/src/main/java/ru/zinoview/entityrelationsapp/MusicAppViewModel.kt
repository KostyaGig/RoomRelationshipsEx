package ru.zinoview.entityrelationsapp

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ru.zinoview.entityrelationsapp.data.albums.Album
import ru.zinoview.entityrelationsapp.data.albums.UserAlbums
import ru.zinoview.entityrelationsapp.data.songs.AlbumSongs
import ru.zinoview.entityrelationsapp.data.songs.AlbumSongsCrossRef
import ru.zinoview.entityrelationsapp.data.songs.Song
import ru.zinoview.entityrelationsapp.data.songs.SongAlbums
import ru.zinoview.entityrelationsapp.data.users.User

class MusicAppViewModel : ViewModel() {

    private val database: MyDatabase
        get() {
            return checkNotNull(MyDatabase.database)
        }

    val users = flow {
        emit(database.userDao().getUsers().orEmpty())
    }.onEach {
        Log.d("zinoviewk", "onEach $it")
    }
        .flowOn(Dispatchers.IO)

    private val _albums = MutableStateFlow<UserAlbums?>(null)
    val albums = _albums.asStateFlow()

    fun loadAlbums(userId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            database.albumsDao().userAlbums(
                userId = userId
            ).also {
                _albums.value = it
            }
        }
    }

    fun addAlbum(
        userId: Long,
        albumTitle: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            database.albumsDao().insertAlbums(
                listOf(
                    Album(
                        title = albumTitle,
                        userIdOwner = userId
                    )
                )
            )
        }
    }

    private val _albumSongs = MutableStateFlow<AlbumSongs?>(null)
    val albumSongs = _albumSongs.asStateFlow()

    fun loadAlbumSongs(albumId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            database.songsDao().albumSongs(
                albumId = albumId
            ).also {
                _albumSongs.value = it
            }
        }
    }

    fun addAlbumSong(
        albumId: Long,
        songTitle: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            // todo it is better to do below insertions in one @Transaction
            val insertedSongIds = database.songsDao().insertSongs(
                listOf(
                    Song(
                        title = songTitle
                    )
                )
            )

            val crossRefs = insertedSongIds.map { songId ->
                AlbumSongsCrossRef(
                    albumId = albumId,
                    songId = songId
                )
            }
            database.songsDao().insertAlbumSongsCrossRef(crossRefs)
        }
    }

    private val _songAlbums = MutableStateFlow<SongAlbums?>(null)
    val songAlbums = _songAlbums.asStateFlow()

    fun loadSongAlbums(songId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            database.songsDao().songAlbums(
                songId = songId
            ).also {
                _songAlbums.value = it
            }
        }
    }

    fun addSongToAlbum(
        songId: Long,
        albumId: Long
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            database.songsDao().insertAlbumSongsCrossRef(
                listOf(
                    AlbumSongsCrossRef(
                        albumId = albumId,
                        songId = songId
                    )
                )
            )
        }
    }

    init {
//        viewModelScope.launch(Dispatchers.IO) {
//            database.userDao().insertUsers(
//                listOf(
//                    User(
//                        name = "Alice"
//                    ),
//                    User(
//                        name = "Bob"
//                    ),
//                    User(
//                        name = "John"
//                    )
//                )
//            )
//        }
//        viewModelScope.launch(Dispatchers.IO) {
//            val users =
//        }
    }
}