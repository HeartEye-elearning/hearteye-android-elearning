package nl.hearteye.elearning.ui.screens.discussions

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import nl.hearteye.elearning.ui.components.buttons.PlusButton
import nl.hearteye.elearning.ui.components.error.ErrorView
import nl.hearteye.elearning.ui.theme.ForegroundPrimary

@Composable
fun DiscussionsScreen(
    discussionViewModel: DiscussionViewModel = hiltViewModel(),
    navController: NavController
) {
    val discussions = discussionViewModel.discussions.value
    val errorMessage = discussionViewModel.errorMessage.value

    LaunchedEffect(Unit) {
        if (discussions.isEmpty() && errorMessage == null) {
            discussionViewModel.getDiscussions()
        }
    }

    Column(modifier = Modifier.padding(16.dp)) {

        if (discussions.isEmpty() && errorMessage == null) {
            CircularProgressIndicator(
                color = ForegroundPrimary
            )
        }

        errorMessage?.let {
            ErrorView(message = "Error: $it") { }
        }

        if (discussions.isNotEmpty()) {
            discussions.forEach { discussionResponse ->
                discussionResponse.content.forEach { discussion ->
                    Text(
                        text = "Title: ${discussion.title}",
                        style = androidx.compose.material3.MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = "Content: ${discussion.content}",
                        style = androidx.compose.material3.MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }

        PlusButton(navController = navController)
    }
}



