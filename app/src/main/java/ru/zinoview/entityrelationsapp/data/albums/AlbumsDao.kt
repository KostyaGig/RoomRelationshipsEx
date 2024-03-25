package ru.zinoview.entityrelationsapp.data.albums

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AlbumsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlbums(albums: List<Album>): List<Long>

    @Query("SELECT * FROM users WHERE userId = :userId LIMIT :limit")
    suspend fun userAlbums(userId: Long, limit: Long = 10): UserAlbums
}