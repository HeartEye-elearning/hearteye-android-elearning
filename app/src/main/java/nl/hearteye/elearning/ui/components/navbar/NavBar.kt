package nl.hearteye.elearning.ui.components.navbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import nl.hearteye.elearning.R
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
                iconRes = R.drawable.ic_home,
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
                iconRes = R.drawable.ic_courses,
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
                iconRes = R.drawable.ic_discussions,
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
                iconRes = R.drawable.ic_more,
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


