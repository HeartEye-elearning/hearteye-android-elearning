package nl.hearteye.elearning

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import nl.hearteye.elearning.ui.navigation.Navigation
import nl.hearteye.elearning.ui.theme.ElearningTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ElearningTheme {
                val navController = rememberNavController()
                Navigation(navController = navController)
            }
        }
    }
}
