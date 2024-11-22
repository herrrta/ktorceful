import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.herrrta.sample.APIService
import dev.herrrta.sample.User
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Users(modifier: Modifier) {
    val scope = rememberCoroutineScope()
    var formVisible by remember { mutableStateOf(false) }
    var selectedUser by remember { mutableStateOf<User?>(null) }
    val users = remember { mutableStateListOf<SwipeItem<User>>() }

    val refreshUsers = {
        scope.launch {
            users.clear()
            users.addAll(
                APIService
                    .getUsers()
                    .mapIndexed { index, user -> SwipeItem(user, index) }
            )
        }
    }

    Column(modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Button(
                onClick = { formVisible = true },
                content = { Text("Add user") }
            )
            Button(
                onClick = { refreshUsers() },
                content = { Text("Load users") }
            )
        }
        HorizontalDivider()
        LazyColumn {
            itemsIndexed(users) { index, swipe ->
                SwipeableItem(
                    isRevealed = swipe.revealed,
                    onExpanded = { users[index] = users[index].copy(revealed = true) },
                    onCollapsed = { users[index] = users[index].copy(revealed = false) },
                    actions = {
                        IconButton(
                            onClick = {
                                scope.launch {
                                    APIService.deleteUser(swipe.item.id)
                                    users.removeAt(index)
                                }
                            },
                            modifier = Modifier
                                .fillMaxHeight()
                                .background(Color.Red)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = null,
                            )
                        }
                        IconButton(
                            onClick = {
                                users[index] = swipe.copy(revealed = false)
                                    .also {
                                        selectedUser = it.item
                                    }
                            },
                            modifier = Modifier
                                .fillMaxHeight()
                                .background(Color.Cyan)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = null,
                            )
                        }
                    },
                ) {
                    ListItem(
                        modifier = Modifier.fillMaxWidth(),
                        headlineContent = { Text("Name: ${swipe.item.fullName}") },
                        leadingContent = { Text("ID: ${swipe.item.id}") },
                        supportingContent = { Text("Active: ${swipe.item.isActive}") },
                    )
                }
            }
        }
    }

    UserForm(
        formVisible,
        selectedUser,
        onDismiss = { selectedUser = null; formVisible = false },
        onSaveClick = { proxy ->
            scope.launch {
                val index = users.indexOfFirst { it.item.id == proxy.id }
                if (index != -1) {
                    APIService.updateUser(proxy)
                    users[index] = users[index].copy(item = proxy)
                }
                else {
                    APIService.addUser(proxy)
                    refreshUsers()
                }
                selectedUser = null
                formVisible = false
            }
        }
    )

    LaunchedEffect(true) { refreshUsers() }
}