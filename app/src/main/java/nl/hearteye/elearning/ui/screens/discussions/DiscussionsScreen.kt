package nl.hearteye.elearning.ui.screens.discussions

import DiscussionsCard
import FilterDialog
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import nl.hearteye.elearning.ui.components.buttons.PlusButton
import nl.hearteye.elearning.ui.components.comments.CommentsOverlay
import nl.hearteye.elearning.ui.components.searchbar.SearchBar
import nl.hearteye.elearning.ui.theme.ForegroundPrimary

@Composable
fun DiscussionsScreen(
    discussionViewModel: DiscussionViewModel = hiltViewModel(),
    navController: NavController
) {
    val discussions = discussionViewModel.discussions.value
    val userCache = discussionViewModel.userCache.value
    val discussionDetail = discussionViewModel.discussionDetail.value
    val searchQuery = discussionViewModel.searchQuery.value
    var isLoading = discussionViewModel.loading.value
    val listState = rememberLazyListState()

    val expandedDiscussionId = remember { mutableStateOf<String?>(null) }
    val currentPage = remember { mutableStateOf(0) }
    val selectedCategory = remember { mutableStateOf<String?>(null) }
    val showMyPostsOnly = remember { mutableStateOf(false) }

    val selectedDiscussionId = remember { mutableStateOf<String?>(null) }

    val showFilterDialog = remember { mutableStateOf(false) }

    val currentUser = discussionViewModel.currentUser.value

    LaunchedEffect(Unit) {
        discussionViewModel.fetchCurrentUser()
    }

    LaunchedEffect(searchQuery) {
        currentPage.value = 0
        discussionViewModel.getDiscussions(page = 0, search = searchQuery)
    }

    val onScrollReachedEnd = {
        val firstVisibleItemIndex = listState.layoutInfo.visibleItemsInfo.firstOrNull()?.index
        val totalItems = listState.layoutInfo.totalItemsCount

        if (firstVisibleItemIndex != null && firstVisibleItemIndex + 3 >= totalItems && !isLoading) {
            isLoading = true
            currentPage.value++
            discussionViewModel.getDiscussions(page = currentPage.value)
        }
    }

    LaunchedEffect(discussions) {
        if (discussions.isNotEmpty()) {
            isLoading = false
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(56.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                SearchBar(
                    value = searchQuery,
                    onValueChange = { query ->
                        discussionViewModel._searchQuery.value = query
                        currentPage.value = 0
                        discussionViewModel.getDiscussions(page = 0, search = query)
                    },
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp)
                )

                IconButton(
                    onClick = {
                        showFilterDialog.value = true
                    },
                    modifier = Modifier
                        .size(56.dp)
                        .background(ForegroundPrimary, shape = RoundedCornerShape(8.dp))
                ) {
                    Icon(
                        imageVector = Icons.Default.Tune,
                        contentDescription = "Filter",
                        tint = Color.White
                    )
                }
            }

            LazyColumn(
                state = listState,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize()
                    .onGloballyPositioned {
                        onScrollReachedEnd()
                    },
                content = {
                    items(discussions) { discussionResponse ->
                        discussionResponse.content.forEach { discussion ->
                            val user = userCache[discussion.userId]
                            val isUserFetched = user != null

                            if (!isUserFetched) {
                                LaunchedEffect(discussion.userId) {
                                    discussionViewModel.getUser(discussion.userId)
                                }
                            }
                            val isExpanded = expandedDiscussionId.value == discussion.id


                            DiscussionsCard(
                                user = user,
                                postTime = discussion.createdAt,
                                postTitle = discussion.title,
                                postContent = if (isExpanded) discussion.content else discussion.content.take(100),
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
                                currentUser = currentUser,
                                onCommentsClick = {
                                    selectedDiscussionId.value = discussion.id
                                },
                                imageLocation = discussion.imageLocation.toString()
                            )
                        }
                    }

                    if (isLoading) {
                        item {
                            CircularProgressIndicator(
                                color = ForegroundPrimary,
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }
                },
            )
        }

        PlusButton(
            navController = navController,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        )

        selectedDiscussionId.value?.let {
            CommentsOverlay(
                discussionDetail = discussionDetail,
                onClose = { selectedDiscussionId.value = null },
                onAddComment = { commentText, parentCommentId ->
                    selectedDiscussionId.value?.let { discussionId ->
                        discussionViewModel.createComment(
                            discussionId,
                            commentText,
                            parentCommentId
                        )
                    }
                },
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }

        if (showFilterDialog.value) {
            FilterDialog(
                selectedCategory = selectedCategory.value,
                showMyPostsOnly = showMyPostsOnly.value,
                onClose = { showFilterDialog.value = false },
                onFilterChange = { category, showMyPosts ->
                    selectedCategory.value = category
                    showMyPostsOnly.value = showMyPosts
                    currentPage.value = 0
                    discussionViewModel.getDiscussions(
                        page = 0,
                        category = category,
                        creator = showMyPosts
                    )
                    showFilterDialog.value = false
                }
            )
        }
    }
}

