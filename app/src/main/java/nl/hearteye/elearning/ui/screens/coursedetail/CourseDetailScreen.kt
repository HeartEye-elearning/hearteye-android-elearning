package nl.hearteye.elearning.ui.screens.coursedetail

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import nl.hearteye.elearning.ui.theme.ForegroundPrimary

@Composable
fun CourseDetailScreen(
    courseId: String,
    courseDetailViewModel: CourseDetailViewModel = hiltViewModel()
) {
    val courseDetail = courseDetailViewModel.courseDetail.value
    val isLoading = courseDetailViewModel.isLoading.value
    val errorMessage = courseDetailViewModel.errorMessage.value

    val currentPageIndex = remember { mutableStateOf(0) }
    val isStarted = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        courseDetailViewModel.fetchCourseDetails(courseId)
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
                Text(
                    text = errorMessage,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            courseDetail != null -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .background(
                            Color.White,
                            shape = RoundedCornerShape(10.dp)
                        )
                        .shadow(5.dp, shape = RoundedCornerShape(10.dp))
                        .padding(16.dp)
                ) {
                    if (!isStarted.value) {
                        Text(
                            text = courseDetail.title,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                                .padding(bottom = 32.dp),
                            style = androidx.compose.material3.MaterialTheme.typography.titleMedium
                        )
                        Button(
                            onClick = {
                                isStarted.value = true
                                currentPageIndex.value = 0
                            },
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(bottom = 16.dp),
                            shape = RoundedCornerShape(10.dp),
                            colors = androidx.compose.material3.ButtonDefaults.buttonColors(containerColor = ForegroundPrimary)
                        ) {
                            Text(text = "Start", color = Color.White)
                        }
                    } else {
                        val currentInformationPage = courseDetail.informationPages.getOrNull(currentPageIndex.value)
                        if (currentInformationPage != null) {
                            Text(
                                text = currentInformationPage.content["eng"] ?: "No content available",
                                modifier = Modifier.padding(bottom = 16.dp)
                            )
                        }

                        Spacer(modifier = Modifier.weight(1f))

                        Row(
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            if (currentPageIndex.value > 0) {
                                Button(
                                    onClick = { currentPageIndex.value-- },
                                    modifier = Modifier.weight(1f),
                                    shape = RoundedCornerShape(10.dp),
                                    border = BorderStroke(1.dp, Color.Red),
                                    colors = androidx.compose.material3.ButtonDefaults.outlinedButtonColors(contentColor = ForegroundPrimary),
                                ) {
                                    Text(
                                        text = "Back",
                                        color = ForegroundPrimary
                                    )
                                }
                            }

                            if (currentPageIndex.value < courseDetail.informationPages.size - 1) {
                                Button(
                                    onClick = { currentPageIndex.value++ },
                                    modifier = Modifier.weight(1f),
                                    shape = RoundedCornerShape(10.dp),
                                    colors = androidx.compose.material3.ButtonDefaults.buttonColors(containerColor = ForegroundPrimary)
                                ) {
                                    Text(text = "Next", color = Color.White)
                                }
                            } else {
                                Text(
                                    text = "You've seen all information pages!",
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


