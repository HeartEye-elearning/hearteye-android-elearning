package nl.hearteye.elearning.ui.components.navigation.navbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ChatBubbleOutline
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.ImportContacts
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import nl.hearteye.elearning.ui.components.navigation.navbaritem.NavBarItem
import nl.hearteye.elearning.ui.navigation.NavRoutes

@Composable
fun NavBar(
    navController: NavController,
    selectedTab: String,
    onTabSelected: (String) -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            NavBarItem(
                iconRes = Icons.Rounded.Home,
                label = "Home",
                selected = selectedTab == NavRoutes.HOME.route,
                onClick = {
                    onTabSelected(NavRoutes.HOME.route)
                    navController.navigate(NavRoutes.HOME.route) {
                        popUpTo(NavRoutes.HOME.route) { inclusive = true }
                    }
                }
            )

            NavBarItem(
                iconRes = Icons.Rounded.ImportContacts,
                label = "Courses",
                selected = selectedTab == NavRoutes.COURSES.route,
                onClick = {
                    onTabSelected(NavRoutes.COURSES.route)
                    navController.navigate(NavRoutes.COURSES.route) {
                        popUpTo(NavRoutes.COURSES.route) { inclusive = true }
                    }
                }
            )

            NavBarItem(
                iconRes = Icons.Rounded.ChatBubbleOutline,
                label = "Discussions",
                selected = selectedTab == NavRoutes.DISCUSSIONS.route,
                onClick = {
                    onTabSelected(NavRoutes.DISCUSSIONS.route)
                    navController.navigate(NavRoutes.DISCUSSIONS.route) {
                        popUpTo(NavRoutes.DISCUSSIONS.route) { inclusive = true }
                    }
                }
            )

            NavBarItem(
                iconRes = Icons.Rounded.Menu,
                label = "More",
                selected = selectedTab == NavRoutes.MORE.route,
                onClick = {
                    onTabSelected(NavRoutes.MORE.route)
                    navController.navigate(NavRoutes.MORE.route) {
                        popUpTo(NavRoutes.MORE.route) { inclusive = true }
                    }
                }
            )
        }
    }
}
