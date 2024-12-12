package nl.hearteye.elearning.ui.screens.home

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import nl.hearteye.elearning.ui.navigation.NavRoutes
import nl.hearteye.elearning.ui.screens.login.LoginViewModel

@Composable
fun HomeScreen(navController: NavController, homeViewModel: HomeViewModel = hiltViewModel()) {
    val loginViewModel: LoginViewModel = hiltViewModel()
    val currentUser = homeViewModel.currentUser.collectAsState().value

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
//        if (currentUser == null) {
//            CircularProgressIndicator()
//        } else {
//            Text(text = "Welcome, ${currentUser.firstName}")
//        }

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
