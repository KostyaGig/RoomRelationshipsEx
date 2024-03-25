package ru.zinoview.entityrelationsapp.data.songs

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface SongsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSongs(song: List<Song>): List<Long>

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlbumSongsCrossRef(albumSongs: List<AlbumSongsCrossRef>): List<Long>

    @Transaction
    @Query("SELECT * FROM albums WHERE albumId = :albumId LIMIT :limit")
    suspend fun albumSongs(albumId: Long, limit: Long = 10): AlbumSongs

    @Transaction
    @Query("SELECT * FROM songs WHERE songId = :songId LIMIT :limit")
    suspend fun songAlbums(songId: Long, limit: Long = 10): SongAlbums
}