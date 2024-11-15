package nl.hearteye.elearning.ui.screens.courses

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import nl.hearteye.elearning.ui.components.coursecard.CourseCard

@Composable
fun CoursesScreen(
    courseViewModel: CoursesViewModel = hiltViewModel()
) {
    val courses = courseViewModel.courses.value

    LaunchedEffect(Unit) {
        courseViewModel.getCourses(language = "eng")
    }

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
                time = "${course.duration}"
            )
        }
    }
}
