package nl.hearteye.elearning.ui.screens.onboarding

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import nl.hearteye.elearning.ui.components.onboarding.Indicator
import nl.hearteye.elearning.ui.components.onboarding.OnboardingPage
import nl.hearteye.elearning.ui.navigation.NavRoutes
import nl.hearteye.elearning.ui.screens.login.LoginViewModel

@Composable
fun OnboardingScreen(navController: NavController, loginViewModel: LoginViewModel = hiltViewModel()) {
    val currentPage = remember { mutableIntStateOf(0) }
    val totalPages = 3
    val isOnboardingCompleted = loginViewModel.isOnboardingCompleted.collectAsState(initial = false)

    LaunchedEffect(isOnboardingCompleted.value) {
        if (isOnboardingCompleted.value) {
            navController.navigate(NavRoutes.HOME.route) {
                popUpTo(NavRoutes.ONBOARDING.route) { inclusive = true }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        when (currentPage.intValue) {
            0 -> OnboardingPage("Onboarding 1", "Description for page 1")
            1 -> OnboardingPage("Onboarding 2", "Description for page 2")
            2 -> OnboardingPage("Onboarding 3", "Description for page 3")
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            repeat(totalPages) { index ->
                Indicator(isSelected = index == currentPage.intValue)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (currentPage.intValue > 0) {
                Button(onClick = { currentPage.intValue-- }) {
                    Text(text = "Back")
                }
            }

            if (currentPage.intValue < totalPages - 1) {
                Button(onClick = { currentPage.intValue++ }) {
                    Text(text = "Next")
                }
            } else {
                Button(onClick = {
                    loginViewModel.completeOnboarding()
                    navController.navigate(NavRoutes.HOME.route) {
                        popUpTo(NavRoutes.ONBOARDING.route) { inclusive = true }
                    }
                }) {
                    Text(text = "Finish")
                }
            }
        }
    }
}
