package ru.zinoview.entityrelationsapp.data.songs

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import ru.zinoview.entityrelationsapp.data.albums.Album

class SongAlbums(
    @Embedded
    val song: Song,
    @Relation(
        parentColumn = "songId",
        entityColumn = "albumId",
        associateBy = Junction(AlbumSongsCrossRef::class)
    )
    val albums: List<Album>
)