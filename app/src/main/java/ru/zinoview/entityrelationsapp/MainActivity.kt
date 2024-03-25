
package ru.zinoview.entityrelationsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import ru.zinoview.entityrelationsapp.ui.theme.EntityRelationsAppTheme

class MainActivity : ComponentActivity() {

    private val usersViewModel by viewModels<MusicAppViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EntityRelationsAppTheme {
                MusicApp(viewModel = usersViewModel)
            }
        }
    }

}
