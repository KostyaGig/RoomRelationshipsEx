package ru.zinoview.entityrelationsapp.data.songs

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "songs")
class Song(
    val title: String,
    @PrimaryKey(autoGenerate = true)
    val songId: Long = 0,
)