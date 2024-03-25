package ru.zinoview.entityrelationsapp.ui.users

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ru.zinoview.entityrelationsapp.MusicAppScreen
import ru.zinoview.entityrelationsapp.MusicAppViewModel

@Composable
fun UsersScreen(
    navController: NavController,
    viewModel: MusicAppViewModel
) {

    val users = viewModel.users.collectAsState(initial = emptyList()).value
    when {
        users.isNotEmpty() -> {
            LazyColumn {
                itemsIndexed(users) { _, user ->
                    Card(
                        modifier = Modifier.padding(all = 20.dp),
                        shape = CircleShape,
                        border = BorderStroke(2.dp, Color.Gray),
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(all = 20.dp)
                                .clickable {
                                    navController.navigate(
                                        route = "${MusicAppScreen.Albums.name}/${user.userId}",
                                    )
                                },
                            text = String.format(
                                "name: name: %s, id: %d",
                                user.name,
                                user.userId
                            ),
                        )
                    }
                }
            }
        }
        else -> {
            Text(text = "No users")
        }
    }
}