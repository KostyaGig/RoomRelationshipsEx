package ru.zinoview.entityrelationsapp.data.users

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    val name: String,
    @PrimaryKey(autoGenerate = true)
    val userId: Long = 0
)