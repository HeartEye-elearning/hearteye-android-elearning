package nl.hearteye.elearning.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import nl.hearteye.elearning.ui.components.navbar.NavBar
import nl.hearteye.elearning.ui.components.topbar.TopBar
import nl.hearteye.elearning.ui.screens.coursedetail.CourseDetailScreen
import nl.hearteye.elearning.ui.screens.courses.CoursesScreen
import nl.hearteye.elearning.ui.screens.discussions.DiscussionsScreen
import nl.hearteye.elearning.ui.screens.home.HomeScreen
import nl.hearteye.elearning.ui.screens.more.MoreScreen

@Composable
fun Navigation(navController: NavHostController) {
    val selectedTab = remember { mutableStateOf("home") }
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route

    Scaffold(
        topBar = {
            TopBar(
                showBackButton = currentRoute == "courseDetail/{courseId}",
                onBackButtonClick = {
                    navController.navigate("courses") {
                        popUpTo("courses") { inclusive = true }
                    }
                }
            )
        },
        bottomBar = {
            if (currentRoute !in listOf("courseDetail/{courseId}")) {
                NavBar(
                    navController = navController,
                    selectedTab = selectedTab.value,
                    onTabSelected = { newTab -> selectedTab.value = newTab }
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("home") { HomeScreen() }
            composable("courses") {
                CoursesScreen(onCourseSelected = { courseId ->
                    navController.navigate("courseDetail/$courseId")
                })
            }
            composable("discussions") { DiscussionsScreen() }
            composable("more") { MoreScreen() }

            composable(
                route = "courseDetail/{courseId}",
                arguments = listOf(navArgument("courseId") { type = NavType.StringType })
            ) { backStackEntry ->
                val courseId = backStackEntry.arguments?.getString("courseId") ?: ""
                CourseDetailScreen(courseId = courseId)
            }
        }
    }
}


