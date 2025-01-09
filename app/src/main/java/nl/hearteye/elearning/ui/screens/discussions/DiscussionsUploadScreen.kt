import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import nl.hearteye.elearning.ui.components.buttons.OutlinedButton
import nl.hearteye.elearning.ui.components.buttons.RegularButton
import nl.hearteye.elearning.ui.components.error.ErrorView
import nl.hearteye.elearning.ui.navigation.NavRoutes
import nl.hearteye.elearning.ui.screens.discussions.DiscussionViewModel
import nl.hearteye.elearning.ui.theme.typography
import nl.hearteye.elearning.ui.utils.uriToFile
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiscussionsUploadScreen(
    discussionViewModel: DiscussionViewModel = hiltViewModel(),
    navController: NavController
) {
    var postTitle by remember { mutableStateOf("") }
    var postDescription by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    var selectedPdfFile by remember { mutableStateOf<File?>(null) }
    val categories = listOf("FINDINGS", "INTERPRETATION", "TROUBLESHOOTING")

    val scrollState = rememberScrollState()

    val errorMessage by discussionViewModel.errorMessage

    val context = LocalContext.current

    val pickPdfLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                selectedPdfFile = uriToFile(context, it)
                if (selectedPdfFile == null || !selectedPdfFile!!.exists()) {
                    Log.e("DiscussionsUploadScreen", "Invalid file selected.")
                }
            }
        }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(48.dp)
        ) {

            if (errorMessage != null) {
                ErrorView(
                    message = errorMessage!!,
                    onRetry = {}
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                Row {
                    OutlinedButton(
                        onClick = { pickPdfLauncher.launch("application/pdf") },
                        text = "Upload ECG"
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                selectedPdfFile?.let {
                    Text(
                        text = "Selected File: ${it.name}",
                        style = typography.bodyMedium,
                    )
                }
            }

            Column(modifier = Modifier.fillMaxWidth()) {
                Text(text = "Post Title", style = MaterialTheme.typography.titleSmall)
                Text(
                    text = "Give your post a clear and descriptive title.",
                    style = MaterialTheme.typography.bodySmall
                )
                TextField(
                    value = postTitle,
                    onValueChange = { postTitle = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(2.dp, shape = MaterialTheme.shapes.medium)
                        .background(Color.White, shape = MaterialTheme.shapes.medium),
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                    ),
                )
            }

            Column(modifier = Modifier.fillMaxWidth()) {
                Text(text = "Post Description", style = MaterialTheme.typography.titleSmall)
                Text(
                    text = "Provide additional details or context for your post.",
                    style = MaterialTheme.typography.bodySmall
                )
                TextField(
                    value = postDescription,
                    onValueChange = { postDescription = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(2.dp, shape = MaterialTheme.shapes.medium)
                        .background(Color.White, shape = MaterialTheme.shapes.medium),
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                    ),
                )
            }

            Column(modifier = Modifier.fillMaxWidth()) {
                Text(text = "Category", style = MaterialTheme.typography.titleSmall)
                Text(
                    text = "Choose the category that best fits your post.",
                    style = MaterialTheme.typography.bodySmall
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
                            .menuAnchor()
                            .shadow(2.dp, shape = MaterialTheme.shapes.medium)
                            .background(Color.White, shape = MaterialTheme.shapes.medium),
                        readOnly = true,
                        placeholder = {
                            Text(
                                text = if (selectedCategory.isEmpty()) "Category" else "",
                                color = Color.Gray
                            )
                        },
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                        )
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
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color.White)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            RegularButton(
                onClick = {
                    if (postTitle.isNotBlank() && postDescription.isNotBlank() && selectedCategory.isNotEmpty() && selectedPdfFile != null) {
                        discussionViewModel.createDiscussion(
                            postTitle, postDescription, selectedCategory, selectedPdfFile!!
                        ) {
                            navController.navigate(NavRoutes.DISCUSSIONS.route)
                        }
                    }
                },
                text = "Post",
                modifier = Modifier.fillMaxWidth(),
                enabled = postTitle.isNotBlank() && postDescription.isNotBlank() && selectedCategory.isNotEmpty() && selectedPdfFile != null
            )
        }
    }
}
