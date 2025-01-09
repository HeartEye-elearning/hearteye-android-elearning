package nl.hearteye.elearning.ui.screens.courses

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import nl.hearteye.elearning.ui.components.course.coursecard.CourseCard
import nl.hearteye.elearning.ui.components.error.ErrorView
import nl.hearteye.elearning.ui.components.searchbar.SearchBar
import nl.hearteye.elearning.ui.theme.ForegroundPrimary


@Composable
fun CoursesScreen(
    courseViewModel: CoursesViewModel = hiltViewModel(),
    onCourseSelected: (String) -> Unit
) {
    val courses = courseViewModel.filteredCourses.value
    val isLoading = courseViewModel.isLoading.value
    val errorMessage = courseViewModel.errorMessage.value
    val searchQuery = remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        courseViewModel.getCourses()
    }

    LaunchedEffect(searchQuery.value) {
        courseViewModel.filterCourses(searchQuery.value)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        when {
            isLoading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = ForegroundPrimary
                )
            }

            errorMessage != null -> {
                Column(modifier = Modifier.fillMaxSize()) {
                    SearchBar(
                        value = searchQuery.value,
                        onValueChange = { searchQuery.value = it },
                        modifier = Modifier.padding(16.dp)
                    )
                    ErrorView(
                        message = errorMessage,
                        onRetry = { courseViewModel.getCourses() }
                    )
                }
            }

            courses.isEmpty() -> {
                Column(modifier = Modifier.fillMaxSize()) {
                    SearchBar(
                        value = searchQuery.value,
                        onValueChange = { searchQuery.value = it },
                        modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp)
                    )
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No courses available",
                            color = Color.Gray
                        )
                    }
                }
            }

            else -> {
                Column(modifier = Modifier.fillMaxSize()) {
                    SearchBar(
                        value = searchQuery.value,
                        onValueChange = { searchQuery.value = it },
                        modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp)
                    )

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 5.dp)
                    ) {
                        items(courses.size) { index ->
                            val course = courses[index]

                            CourseCard(
                                title = course.title,
                                time = "${course.duration}",
                                imageLocation = course.imageContent ?: "",
                                onClick = { onCourseSelected(course.id) }
                            )
                        }
                    }
                }
            }
        }
    }
}



