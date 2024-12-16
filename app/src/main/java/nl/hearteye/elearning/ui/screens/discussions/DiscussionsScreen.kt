package nl.hearteye.elearning.ui.screens.discussions

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun DiscussionsScreen(
    discussionViewModel: DiscussionViewModel = hiltViewModel()
) {
    val discussions = discussionViewModel.discussions.value

    // Use LaunchedEffect to call getDiscussions only once when the Composable is first launched
    LaunchedEffect(Unit) {
        if (discussions.isEmpty()) {
            discussionViewModel.getDiscussions()
        }
    }

    Column(modifier = Modifier.padding(16.dp)) {
        if (discussions.isEmpty()) {
            Text(
                text = "No discussions available",
                style = androidx.compose.material3.MaterialTheme.typography.bodyLarge
            )
        } else {
            val firstDiscussion = discussions.firstOrNull()?.content?.firstOrNull()

            firstDiscussion?.let {
                Text(
                    text = "Title: ${it.title}",
                    style = androidx.compose.material3.MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "Content: ${it.content}",
                    style = androidx.compose.material3.MaterialTheme.typography.bodyMedium
                )
            } ?: run {
                Text(
                    text = "No content available",
                    style = androidx.compose.material3.MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}
