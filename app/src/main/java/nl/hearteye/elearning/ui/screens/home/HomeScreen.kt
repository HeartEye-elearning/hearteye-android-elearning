package nl.hearteye.elearning.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import nl.hearteye.elearning.ui.navigation.NavRoutes
import nl.hearteye.elearning.ui.screens.login.LoginViewModel

@Composable
fun HomeScreen(navController: NavController) {
    val loginViewModel: LoginViewModel = hiltViewModel()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {
                loginViewModel.logout()
                navController.navigate(NavRoutes.LOGIN.route) {
                    popUpTo(NavRoutes.HOME.route) { inclusive = true }
                }
            }
        ) {
            Text(text = "Logout")
        }
    }
}
