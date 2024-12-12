package nl.hearteye.elearning.ui.screens.onboarding

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import nl.hearteye.elearning.ui.components.onboarding.OnboardingStep1
import nl.hearteye.elearning.ui.components.onboarding.OnboardingStep2
import nl.hearteye.elearning.ui.components.onboarding.OnboardingStep3
import nl.hearteye.elearning.ui.navigation.NavRoutes
import nl.hearteye.elearning.ui.screens.login.LoginViewModel

@Composable
fun OnboardingScreen(
    navController: NavController,
    loginViewModel: LoginViewModel = hiltViewModel()
) {
    val isOnboardingCompleted = loginViewModel.isOnboardingCompleted.collectAsState().value

    LaunchedEffect(isOnboardingCompleted) {
        if (isOnboardingCompleted) {
            navController.navigate(NavRoutes.HOME.route) {
                popUpTo(NavRoutes.ONBOARDING.route) { inclusive = true }
            }
        }
    }

    val currentStep = remember { mutableIntStateOf(1) }

    when (currentStep.intValue) {
        1 -> OnboardingStep1(
            onNextClick = { currentStep.intValue = 2 }
        )

        2 -> OnboardingStep2(
            onNextClick = { currentStep.intValue = 3 },
            onBackClick = { currentStep.intValue = 1 }
        )

        3 -> OnboardingStep3(
            onContinueClick = { selectedLanguage ->
                loginViewModel.completeOnboarding()
                loginViewModel.saveLanguagePreference(selectedLanguage)
                navController.navigate(NavRoutes.HOME.route) {
                    popUpTo(NavRoutes.ONBOARDING.route) { inclusive = true }
                }
            },
            onBackClick = { currentStep.intValue = 2 }
        )
    }
}




