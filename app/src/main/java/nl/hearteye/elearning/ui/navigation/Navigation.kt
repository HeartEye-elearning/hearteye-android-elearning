package nl.hearteye.elearning.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import nl.hearteye.elearning.ui.components.navigation.navbar.NavBar
import nl.hearteye.elearning.ui.components.topbar.TopBar
import nl.hearteye.elearning.ui.screens.LoginScreen
import nl.hearteye.elearning.ui.screens.LoginViewModel
import nl.hearteye.elearning.ui.screens.PreLoginScreen
import nl.hearteye.elearning.ui.screens.answeroverview.AnswerOverviewScreen
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

    val loginViewModel: LoginViewModel = hiltViewModel()
    val isUserLoggedIn = loginViewModel.isUserLoggedIn()

    Scaffold(
        topBar = {
            if (currentRoute !in listOf(NavRoutes.LOGIN.route, NavRoutes.PRELOGIN.route)) {
                TopBar(
                    showBackButton = currentRoute in listOf(
                        NavRoutes.COURSE_DETAIL.route,
                        NavRoutes.ANSWER_OVERVIEW.route
                    ),
                    onBackButtonClick = {
                        when (currentRoute) {
                            NavRoutes.COURSE_DETAIL.route -> {
                                navController.navigate(NavRoutes.COURSES.route) {
                                    popUpTo(NavRoutes.COURSES.route) { inclusive = true }
                                }
                            }
                            NavRoutes.ANSWER_OVERVIEW.route -> {
                                navController.navigate(NavRoutes.COURSES.route) {
                                    popUpTo(NavRoutes.COURSES.route) { inclusive = true }
                                }
                            }
                        }
                    }
                )
            }
        },
        bottomBar = {
            if (currentRoute !in listOf(NavRoutes.LOGIN.route, NavRoutes.PRELOGIN.route)) {
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
            startDestination = if (isUserLoggedIn) NavRoutes.HOME.route else NavRoutes.PRELOGIN.route, // Conditional landing page
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(NavRoutes.LOGIN.route) {
                LoginScreen(
                    onForgotPasswordClick = { },
                    navController = navController
                )
            }
            composable(NavRoutes.PRELOGIN.route) {
                PreLoginScreen(onSignInClick = {
                    navController.navigate(NavRoutes.LOGIN.route) {
                        popUpTo(NavRoutes.PRELOGIN.route) { inclusive = true }
                    }
                })
            }
            composable(NavRoutes.HOME.route) {
                HomeScreen(navController = navController)
            }
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
                CourseDetailScreen(courseId = courseId, navController = navController)
            }
            composable(
                route = NavRoutes.ANSWER_OVERVIEW.route,
                arguments = listOf(navArgument("courseId") { type = NavType.StringType })
            ) { backStackEntry ->
                val courseId = backStackEntry.arguments?.getString("courseId") ?: ""
                AnswerOverviewScreen(courseId = courseId)
            }
        }
    }
}




