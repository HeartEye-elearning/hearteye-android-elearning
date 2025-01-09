package nl.hearteye.elearning.ui.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import nl.hearteye.elearning.ui.components.home.CourseCardHome
import nl.hearteye.elearning.ui.components.home.DiscussionCardHome
import nl.hearteye.elearning.ui.theme.ForegroundPrimary
import nl.hearteye.elearning.ui.theme.typography

@Composable
fun HomeScreen(homeViewModel: HomeViewModel = hiltViewModel()) {
    val courses = homeViewModel.courses.value
    val discussions = homeViewModel.discussions.value
    val isLoading = homeViewModel.isLoading.value
    val errorMessage = homeViewModel.errorMessage.value
    val currentUser = homeViewModel.currentUser.value

    LaunchedEffect(Unit) {
        homeViewModel.getCourses()
        homeViewModel.getDiscussions()
        homeViewModel.fetchCurrentUser()
    }

    if (isLoading) {
        CircularProgressIndicator(
            color = ForegroundPrimary
        )
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text(
                    text = "Welcome, ${currentUser?.firstName ?: ""}",
                    style = typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }

            item {
                Text(
                    text = "Discover essential information for the HeartEye ECG",
                    style = typography.bodyLarge,
                    color = Color.Gray
                )
            }

            // Courses section
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Courses",
                        style = typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "View all courses",
                        style = typography.bodyMedium,
                        color = ForegroundPrimary,
                        modifier = Modifier.clickable { }
                    )
                }
            }

            item {
                if (homeViewModel.isLoading.value) {
                    CircularProgressIndicator(
                        color = ForegroundPrimary
                    )
                } else if (!errorMessage.isNullOrEmpty()) {
                    Text("Error: $errorMessage", color = Color.Red)
                } else {
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(minOf(courses.size, 3)) { index ->
                            val course = courses[index]
                            CourseCardHome(
                                title = course.title,
                                time = course.duration.toString(),
                                onClick = { },
                                image = course.imageContent.toString(),
                            )
                        }
                    }
                }
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Discussions",
                        style = typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "View all discussions",
                        style = typography.bodyMedium,
                        color = ForegroundPrimary,
                        modifier = Modifier.clickable { }
                    )
                }
            }

            item {
                if (homeViewModel.isLoading.value) {
                    CircularProgressIndicator(
                        color = ForegroundPrimary
                    )
                } else if (!errorMessage.isNullOrEmpty()) {
                    Text("Error: $errorMessage", color = Color.Red)
                } else {
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(discussions) { discussionResponse ->
                            discussionResponse.content.takeLast(3).forEach { discussion ->
                                DiscussionCardHome(
                                    title = discussion.title,
                                    timeAgo = discussion.createdAt,
                                    onClick = { },
                                    image = discussion.imageLocation.toString()
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

