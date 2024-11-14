package nl.hearteye.elearning.ui.components.navbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import nl.hearteye.elearning.ui.components.navbaritem.NavBarItem
import nl.hearteye.elearning.R

@Composable
fun NavBar(navItems: List<NavBarItemData>) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            navItems.forEach { item ->
                NavBarItem(iconRes = item.iconRes, label = item.label, onClick = item.onClick)
            }
        }
    }
}


data class NavBarItemData(
    val iconRes: Int,
    val label: String,
    val onClick: () -> Unit
)

@Preview
@Composable
fun PreviewNavBar() {
    val navItems = listOf(
        NavBarItemData(R.drawable.ic_home, "Home") {},
        NavBarItemData(R.drawable.ic_courses, "Courses") {},
        NavBarItemData(R.drawable.ic_discussions, "Discussions") {},
        NavBarItemData(R.drawable.ic_more, "More") {}
    )
    NavBar(navItems = navItems)
}