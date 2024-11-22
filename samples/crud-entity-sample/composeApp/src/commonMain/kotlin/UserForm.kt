import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.herrrta.sample.User


@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun UserForm(
    isVisible: Boolean,
    user: User?,
    onDismiss: () -> Unit,
    onSaveClick: (user: User) -> Unit
) {
    if (isVisible) {
        ModalBottomSheet(
            onDismissRequest = onDismiss,
        ) {
            var proxyItem by remember { mutableStateOf(user?.copy() ?: User()) }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = user?.let { "Modifying: ${it.fullName}" } ?: "Adding User",
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )
                Spacer(Modifier.padding(vertical = 8.dp))
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("First Name") },
                    value = proxyItem.firstName,
                    onValueChange = { proxyItem = proxyItem.copy(firstName = it) }
                )
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Last Name") },
                    value = proxyItem.lastName,
                    onValueChange = { proxyItem = proxyItem.copy(lastName = it) }
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Is Active?")
                    Checkbox(
                        checked = proxyItem.isActive,
                        onCheckedChange = { proxyItem = proxyItem.copy(isActive = it) }
                    )
                }

                Row {
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { onSaveClick(proxyItem) }
                    ) {
                        Text("Save")
                    }
                }
            }
        }
    }
}