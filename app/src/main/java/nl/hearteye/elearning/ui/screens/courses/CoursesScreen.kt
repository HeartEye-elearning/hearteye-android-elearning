package nl.hearteye.elearning.ui.screens.courses

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import nl.hearteye.elearning.ui.components.coursecard.CourseCard
import nl.hearteye.elearning.ui.components.searchbar.SearchBar

@Composable
fun CoursesScreen(
    courseViewModel: CoursesViewModel = hiltViewModel()
) {
    val courses = courseViewModel.filteredCourses.value
    val searchQuery = remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        courseViewModel.getCourses(language = "eng")
    }

    LaunchedEffect(searchQuery.value) {
        courseViewModel.filterCourses(searchQuery.value)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        SearchBar(
            value = TextFieldValue(searchQuery.value),
            onValueChange = { searchQuery.value = it.text },
            modifier = Modifier.padding(20.dp)
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
                    time = "${course.duration}"
                )
            }
        }
    }
}


