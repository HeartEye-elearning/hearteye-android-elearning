package nl.hearteye.elearning.ui.screens.discussions

import DiscussionsCard
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import nl.hearteye.elearning.ui.components.buttons.PlusButton
import nl.hearteye.elearning.ui.components.error.ErrorView
import nl.hearteye.elearning.ui.theme.ForegroundPrimary
import nl.hearteye.elearning.R
import nl.hearteye.elearning.data.model.User

@Composable
fun DiscussionsScreen(
    discussionViewModel: DiscussionViewModel = hiltViewModel(),
    navController: NavController
) {
    val discussions = discussionViewModel.discussions.value
    val errorMessage = discussionViewModel.errorMessage.value
    val userCache = discussionViewModel.userCache.value

    LaunchedEffect(Unit) {
        if (discussions.isEmpty() && errorMessage == null) {
            discussionViewModel.getDiscussions()
        }
    }

    LazyColumn(modifier = Modifier.padding(16.dp)) {

        if (discussions.isEmpty() && errorMessage == null) {
            item {
                CircularProgressIndicator(
                    color = ForegroundPrimary,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }

        errorMessage?.let {
            item {
                ErrorView(message = "Error: $it") { }
            }
        }

        if (discussions.isNotEmpty()) {
            items(discussions.size) { index ->
                val discussionResponse = discussions[index]
                discussionResponse.content.forEach { discussion ->
                    val user = userCache[discussion.userId]

                    if (user == null) {
                        LaunchedEffect(discussion.userId) {
                            discussionViewModel.getUser(discussion.userId)
                        }
                    } else {
                        DiscussionsCard(
                            user = user,
                            postTime = discussion.createdAt,
                            postTitle = discussion.title,
                            postContent = discussion.content,
                            ecgImageResId = R.drawable.ecg_scan
                        )
                    }
                }
            }
        }

        item {
            PlusButton(navController = navController)
        }
    }
}
