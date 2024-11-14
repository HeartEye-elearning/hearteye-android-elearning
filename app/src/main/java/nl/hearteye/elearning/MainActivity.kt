package nl.hearteye.elearning

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import nl.hearteye.elearning.ui.components.navbar.NavBar
import nl.hearteye.elearning.ui.components.navbar.NavBarItemData
import nl.hearteye.elearning.ui.theme.ElearningTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ElearningTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        NavBar(
                            navItems = listOf(
                                NavBarItemData(R.drawable.ic_home, "Home") {},
                                NavBarItemData(R.drawable.ic_courses, "Courses") {},
                                NavBarItemData(R.drawable.ic_discussions, "Discussions") {},
                                NavBarItemData(R.drawable.ic_more, "More") {}
                            )
                        )
                    }
                ) { paddingValues ->
                    Text(
                        text = "Main content goes here",
                        modifier = Modifier
                            .padding(paddingValues)
                    )
                }
            }
        }
    }
}
