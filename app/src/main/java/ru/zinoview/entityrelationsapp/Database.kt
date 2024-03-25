package ru.zinoview.entityrelationsapp

import android.content.Context
import android.os.Looper
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.zinoview.entityrelationsapp.data.albums.Album
import ru.zinoview.entityrelationsapp.data.albums.AlbumsDao
import ru.zinoview.entityrelationsapp.data.songs.AlbumSongsCrossRef
import ru.zinoview.entityrelationsapp.data.songs.Song
import ru.zinoview.entityrelationsapp.data.songs.SongsDao
import ru.zinoview.entityrelationsapp.data.users.User
import ru.zinoview.entityrelationsapp.data.users.UserDao

@Database(entities = [User::class, Album::class, Song::class, AlbumSongsCrossRef::class], version = 1)
abstract class MyDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun albumsDao(): AlbumsDao
    abstract fun songsDao(): SongsDao

    companion object {

        @Volatile
        var database: MyDatabase? = null
            get() {
                if (field == null) {
                    error("Db is not yet initialized!!!")
                }
                return field
            }

        fun init(context: Context) {
            if (Thread.currentThread() != Looper.getMainLooper().thread) {
                error("Database should be initialized on main thread")
            }
            database = Room
                .databaseBuilder(
                    context,
                    MyDatabase::class.java,
                    "my-database"
                )
                .build()
        }
    }
}