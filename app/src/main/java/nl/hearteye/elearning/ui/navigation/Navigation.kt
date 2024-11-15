package nl.hearteye.elearning.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import nl.hearteye.elearning.ui.components.navbar.NavBar
import nl.hearteye.elearning.ui.screens.courses.CoursesScreen
import nl.hearteye.elearning.ui.screens.discussions.DiscussionsScreen
import nl.hearteye.elearning.ui.screens.home.HomeScreen
import nl.hearteye.elearning.ui.screens.more.MoreScreen

@Composable
fun Navigation(navController: NavHostController) {
    val selectedTab = remember { mutableStateOf("home") }

    Scaffold(
        bottomBar = {
            NavBar(
                navController = navController,
                selectedTab = selectedTab.value,
                onTabSelected = { newTab -> selectedTab.value = newTab }
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("home") { HomeScreen() }
            composable("courses") { CoursesScreen() }
            composable("discussions") { DiscussionsScreen() }
            composable("more") { MoreScreen() }
        }
    }
}

