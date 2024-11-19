package nl.hearteye.elearning.ui.components.navbar

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
import nl.hearteye.elearning.ui.components.navbaritem.NavBarItem

@Composable
fun NavBar(
    navController: NavController,
    selectedTab: String,
    onTabSelected: (String) -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
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
                selected = selectedTab == "home",
                onClick = {
                    onTabSelected("home")
                    navController.navigate("home") {
                        popUpTo("home") { inclusive = true }
                    }
                }
            )

            NavBarItem(
                iconRes = Icons.Rounded.ImportContacts,
                label = "Courses",
                selected = selectedTab == "courses",
                onClick = {
                    onTabSelected("courses")
                    navController.navigate("courses") {
                        popUpTo("courses") { inclusive = true }
                    }
                }
            )

            NavBarItem(
                iconRes = Icons.Rounded.ChatBubbleOutline,
                label = "Discussions",
                selected = selectedTab == "discussions",
                onClick = {
                    onTabSelected("discussions")
                    navController.navigate("discussions") {
                        popUpTo("discussions") { inclusive = true }
                    }
                }
            )

            NavBarItem(
                iconRes = Icons.Rounded.Menu,
                label = "More",
                selected = selectedTab == "more",
                onClick = {
                    onTabSelected("more")
                    navController.navigate("more") {
                        popUpTo("more") { inclusive = true }
                    }
                }
            )
        }
    }
}


