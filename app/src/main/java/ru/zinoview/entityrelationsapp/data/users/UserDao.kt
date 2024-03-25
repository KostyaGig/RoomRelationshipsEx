package ru.zinoview.entityrelationsapp.data.users

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(users: List<User>): List<Long>

    @Query("SELECT * FROM users LIMIT :limit")
    suspend fun getUsers(limit: Long = 10): List<User>
}