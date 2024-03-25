package ru.zinoview.entityrelationsapp.data.albums

import androidx.room.Embedded
import androidx.room.Relation
import ru.zinoview.entityrelationsapp.data.users.User


data class UserAlbums(
    @Embedded
    val user: User,
    @Relation(
        parentColumn = "userId",
        entityColumn = "userIdOwner"
    )
    val albums: List<Album>
)