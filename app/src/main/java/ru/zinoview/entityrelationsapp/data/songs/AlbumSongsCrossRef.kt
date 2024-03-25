package ru.zinoview.entityrelationsapp.data.songs

import androidx.room.Entity

@Entity(primaryKeys = ["albumId", "songId"])
class AlbumSongsCrossRef(
    val albumId: Long,
    val songId: Long
)