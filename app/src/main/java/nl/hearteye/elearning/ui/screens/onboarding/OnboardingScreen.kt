package nl.hearteye.elearning.ui.screens.onboarding

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import nl.hearteye.elearning.R
import nl.hearteye.elearning.ui.components.buttons.RegularButton
import nl.hearteye.elearning.ui.components.onboarding.Indicator
import nl.hearteye.elearning.ui.components.onboarding.OnboardingPage
import nl.hearteye.elearning.ui.navigation.NavRoutes
import nl.hearteye.elearning.ui.screens.login.LoginViewModel

@Composable
fun OnboardingScreen(
    navController: NavController,
    loginViewModel: LoginViewModel = hiltViewModel()
) {
    val currentPage = remember { mutableIntStateOf(0) }
    val totalPages = 3
    val isOnboardingCompleted = loginViewModel.isOnboardingCompleted.collectAsState().value

//    LaunchedEffect(isOnboardingCompleted) {
//        if (isOnboardingCompleted) {
//            navController.navigate(NavRoutes.HOME.route) {
//                popUpTo(NavRoutes.ONBOARDING.route) { inclusive = true }
//            }
//        }
//    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        OnboardingPage(
            title = when (currentPage.intValue) {
                0 -> "Why this e-learning course"
                1 -> "Onboarding 2"
                else -> "Onboarding 3"
            },
            description = when (currentPage.intValue) {
                0 -> "Learn the key differences between a standard ECG and the HeartEye MiniECG. This knowledge is essential for accurate interpretation and patient care."
                1 -> "Description for page 2"
                else -> "Description for page 3"
            },
            imageResource = R.drawable.quiz_cover,
            currentPage = currentPage.intValue,
            totalPages = totalPages,
            onNext = {
                if (currentPage.intValue < totalPages - 1) {
                    currentPage.intValue++
                }
            },
            onBack = {
                if (currentPage.intValue > 0) {
                    currentPage.intValue--
                }
            },
            onFinish = {
                loginViewModel.completeOnboarding()
                navController.navigate(NavRoutes.HOME.route) {
                    popUpTo(NavRoutes.ONBOARDING.route) { inclusive = true }
                }
            }
        )
    }
}

