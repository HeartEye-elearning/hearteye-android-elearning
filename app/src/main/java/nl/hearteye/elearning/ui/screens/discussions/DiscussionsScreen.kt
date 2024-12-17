package nl.hearteye.elearning.ui.screens.discussions

import DiscussionsCard
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import nl.hearteye.elearning.R
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
    val userCache = discussionViewModel.userCache.value
    val discussionDetail = discussionViewModel.discussionDetail.value

    val listState = rememberLazyListState()

    val expandedDiscussionId = remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        if (discussions.isEmpty() && errorMessage == null) {
            discussionViewModel.getDiscussions()
        }
    }

    LaunchedEffect(listState.layoutInfo.visibleItemsInfo.lastOrNull()) {
        val lastItem = listState.layoutInfo.visibleItemsInfo.lastOrNull()
        if (lastItem != null && lastItem.index == discussions.size - 1) {
            discussionViewModel.loadMoreDiscussions()
        }
    }

    LaunchedEffect(Unit) {
        if (discussions.isEmpty() && errorMessage == null) {
            discussionViewModel.getDiscussions()
            discussionViewModel.fetchCurrentUser()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            state = listState,
            modifier = Modifier.padding(16.dp).fillMaxSize()
        ) {
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
                        val currentUser =
                            userCache[discussionViewModel.userCache.value.keys.firstOrNull()]

                        if (user == null) {
                            LaunchedEffect(discussion.userId) {
                                discussionViewModel.getUser(discussion.userId)
                            }
                        } else {
                            val isExpanded = expandedDiscussionId.value == discussion.id

                            DiscussionsCard(
                                user = user,
                                postTime = discussion.createdAt,
                                postTitle = discussion.title,
                                postContent = if (isExpanded) discussion.content else discussion.content.take(
                                    100
                                ),
                                ecgImageResId = R.drawable.ecg_scan,
                                discussionId = discussion.id,
                                isExpanded = isExpanded,
                                onReadMoreClick = { discussionId ->
                                    if (expandedDiscussionId.value == discussionId) {
                                        expandedDiscussionId.value = null
                                    } else {
                                        expandedDiscussionId.value = discussionId
                                        discussionViewModel.getDiscussionById(discussionId)
                                    }
                                },
                                discussionDetail = if (isExpanded) discussionDetail else null,
                                isCurrentUser = currentUser?.id == discussion.userId
                            )
                        }
                    }
                }
            }
        }

        PlusButton(
            navController = navController,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        )
    }
}
