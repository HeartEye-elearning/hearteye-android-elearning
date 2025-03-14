package nl.hearteye.elearning.ui.navigation

import DiscussionsUploadScreen
import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import nl.hearteye.elearning.ui.components.popup.Popup
import nl.hearteye.elearning.ui.components.topbar.TopBar
import nl.hearteye.elearning.ui.screens.login.LoginScreen
import nl.hearteye.elearning.ui.screens.login.LoginViewModel
import nl.hearteye.elearning.ui.screens.login.PreLoginScreen
import nl.hearteye.elearning.ui.screens.answeroverview.AnswerOverviewScreen
import nl.hearteye.elearning.ui.screens.coursedetail.CourseDetailScreen
import nl.hearteye.elearning.ui.screens.coursedetail.CourseDetailViewModel
import nl.hearteye.elearning.ui.screens.courses.CoursesScreen
import nl.hearteye.elearning.ui.screens.discussions.DiscussionsScreen
import nl.hearteye.elearning.ui.screens.home.HomeScreen
import nl.hearteye.elearning.ui.screens.more.MoreScreen
import nl.hearteye.elearning.ui.screens.onboarding.OnboardingScreen

@Composable
fun Navigation(navController: NavHostController) {
    val selectedTab = remember { mutableStateOf(NavRoutes.HOME.route) }
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route

    val loginViewModel: LoginViewModel = hiltViewModel()

    val isUserLoggedIn = loginViewModel.isUserLoggedIn()
    val isOnboardingCompleted = loginViewModel.isOnboardingCompleted.collectAsState(initial = false).value

    val showPopup = remember { mutableStateOf(false) }

    val courseDetailViewModel: CourseDetailViewModel = hiltViewModel()

    val onBackButtonClick = {
        if (currentRoute == NavRoutes.COURSE_DETAIL.route) {
            showPopup.value = true
        }
    }

    Scaffold(
        topBar = {
            if (currentRoute !in listOf(NavRoutes.LOGIN.route, NavRoutes.PRELOGIN.route, NavRoutes.ONBOARDING.route)) {
                TopBar(
                    showBackButton = currentRoute in listOf(NavRoutes.COURSE_DETAIL.route),
                    onBackButtonClick = onBackButtonClick
                )
            }
        },
        bottomBar = {
            if (currentRoute !in listOf(NavRoutes.LOGIN.route, NavRoutes.PRELOGIN.route, NavRoutes.ONBOARDING.route, NavRoutes.COURSE_DETAIL.route, NavRoutes.ANSWER_OVERVIEW.route)) {
                NavBar(
                    navController = navController,
                    selectedTab = selectedTab.value,
                    onTabSelected = { newTab -> selectedTab.value = newTab }
                )
            }
        }
    ) { innerPadding ->

        val startDestination = when {
            isUserLoggedIn && isOnboardingCompleted -> NavRoutes.HOME.route
            isUserLoggedIn -> NavRoutes.ONBOARDING.route
            else -> NavRoutes.PRELOGIN.route
        }

        NavHost(
            navController = navController,
            startDestination = startDestination,
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
            composable(NavRoutes.ONBOARDING.route) {
                OnboardingScreen(navController)
            }
            composable(NavRoutes.HOME.route) {
                HomeScreen(homeViewModel = hiltViewModel(), navController = navController)
            }
            composable(NavRoutes.COURSES.route) {
                CoursesScreen(onCourseSelected = { courseId ->
                    navController.navigate("courseDetail/$courseId")
                })
            }
            composable(NavRoutes.DISCUSSIONS.route) { DiscussionsScreen(navController = navController) }
            composable(NavRoutes.DISCUSSIONS_UPLOAD.route) {
                DiscussionsUploadScreen(discussionViewModel = hiltViewModel(), navController = navController)
            }
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
                AnswerOverviewScreen(courseId = courseId, navController = navController)
            }
        }

        if (showPopup.value) {
            Popup(
                isVisible = showPopup.value,
                title = "Are you sure?",
                text = "You will lose unsaved data. Do you want to go back?",
                confirmButtonText = "Yes",
                cancelButtonText = "No",
                onConfirm = {
                    val quizId = courseDetailViewModel.getQuizId()
                    courseDetailViewModel.finishQuiz(quizId.toString())

                    navController.navigate(NavRoutes.COURSES.route) {
                        popUpTo(NavRoutes.COURSES.route) { inclusive = true }
                    }
                    showPopup.value = false
                },
                onCancel = {
                    showPopup.value = false
                },
                onDismiss = {
                    showPopup.value = false
                }
            )
        }
    }
}

