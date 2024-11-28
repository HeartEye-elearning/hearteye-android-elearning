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
import nl.hearteye.elearning.ui.components.navigation.navbar.NavBar
import nl.hearteye.elearning.ui.components.topbar.TopBar
import nl.hearteye.elearning.ui.screens.coursedetail.CourseDetailScreen
import nl.hearteye.elearning.ui.screens.courses.CoursesScreen
import nl.hearteye.elearning.ui.screens.discussions.DiscussionsScreen
import nl.hearteye.elearning.ui.screens.home.HomeScreen
import nl.hearteye.elearning.ui.screens.more.MoreScreen

@Composable
fun Navigation(navController: NavHostController) {
    val selectedTab = remember { mutableStateOf(NavRoutes.HOME.route) }
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route

    Scaffold(
        topBar = {
            TopBar(
                showBackButton = currentRoute == NavRoutes.COURSE_DETAIL.route,
                onBackButtonClick = {
                    navController.navigate(NavRoutes.COURSES.route) {
                        popUpTo(NavRoutes.COURSES.route) { inclusive = true }
                    }
                }
            )
        },
        bottomBar = {
            if (currentRoute !in listOf(NavRoutes.COURSE_DETAIL.route)) {
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
            startDestination = NavRoutes.HOME.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(NavRoutes.HOME.route) { HomeScreen() }
            composable(NavRoutes.COURSES.route) {
                CoursesScreen(onCourseSelected = { courseId ->
                    navController.navigate("courseDetail/$courseId")
                })
            }
            composable(NavRoutes.DISCUSSIONS.route) { DiscussionsScreen() }
            composable(NavRoutes.MORE.route) { MoreScreen() }

            composable(
                route = NavRoutes.COURSE_DETAIL.route,
                arguments = listOf(navArgument("courseId") { type = NavType.StringType })
            ) { backStackEntry ->
                val courseId = backStackEntry.arguments?.getString("courseId") ?: ""
                CourseDetailScreen(courseId = courseId)
            }
        }
    }
}


