package nl.hearteye.elearning.ui.screens.discussions

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import nl.hearteye.elearning.ui.components.buttons.OutlinedButton
import nl.hearteye.elearning.ui.components.buttons.RegularButton
import nl.hearteye.elearning.ui.theme.typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiscussionsUploadScreen() {
    var postTitle by remember { mutableStateOf("") }
    var postDescription by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    val categories = listOf("Findings", "Interpretation", "Best practices", "Case studies", "Troubleshooting")

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                OutlinedButton(onClick = {}, text = "Upload ECG")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(text = "Post Title", style = typography.titleSmall)
                    Text(
                        text = "Give your post a clear and descriptive title.",
                        style = typography.bodySmall
                    )
                    TextField(
                        value = postTitle,
                        onValueChange = { postTitle = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text(text = "Enter title") }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(text = "Post Description", style = typography.titleSmall)
                    Text(
                        text = "Provide additional details or context for your post.",
                        style = typography.bodySmall
                    )
                    TextField(
                        value = postDescription,
                        onValueChange = { postDescription = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text(text = "Enter description") }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(text = "Category", style = typography.titleSmall)
                    Text(
                        text = "Choose the category that best fits your post.",
                        style = typography.bodySmall
                    )

                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = it },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        TextField(
                            value = selectedCategory,
                            onValueChange = { selectedCategory = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor(),
                            placeholder = { Text(text = "Select category") },
                            readOnly = true
                        )

                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            categories.forEach { category ->
                                DropdownMenuItem(
                                    text = { Text(text = category) },
                                    onClick = {
                                        selectedCategory = category
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                RegularButton(onClick = {}, text = "Post")
            }
        }
    }
}
