package ru.zinoview.entityrelationsapp.data.songs

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import ru.zinoview.entityrelationsapp.data.albums.Album

class AlbumSongs(
    @Embedded
    val album: Album,
    @Relation(
        parentColumn = "albumId",
        entityColumn = "songId",
        associateBy = Junction(AlbumSongsCrossRef::class)
    )
    val songs: List<Song>
)