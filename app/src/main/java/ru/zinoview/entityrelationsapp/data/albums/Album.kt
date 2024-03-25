package ru.zinoview.entityrelationsapp.data.albums

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "albums")
class Album(
    val title: String,
    val userIdOwner: Long,
    @PrimaryKey(autoGenerate = true)
    val albumId: Long = 0
)