package nl.hearteye.elearning.ui.components.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import nl.hearteye.elearning.R
import nl.hearteye.elearning.ui.components.buttons.OutlinedButton
import nl.hearteye.elearning.ui.components.buttons.RegularButton
import nl.hearteye.elearning.ui.theme.typography

@Composable
fun OnboardingPage(
    title: String,
    description: String,
    imageResource: Int,
    currentPage: Int,
    totalPages: Int,
    onNext: () -> Unit,
    onBack: () -> Unit,
    onFinish: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Image(
            painter = painterResource(id = R.drawable.hearteye_logo_black),
            contentDescription = "HeartEye Logo",
            modifier = Modifier
                .fillMaxWidth()
                .height(25.dp)
                .align(Alignment.CenterHorizontally)
        )

        Column(
            horizontalAlignment = Alignment.Start,
        ) {
            Text(
                text = title,
                style = typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = description,
                style = typography.bodyMedium,
            )
        }

        Image(
            painter = painterResource(id = imageResource),
            contentDescription = "Onboarding Image",
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (currentPage > 0) {
                OutlinedButton(
                    onClick = onBack,
                    modifier = Modifier.weight(1f),
                    text = "Back"
                )
            }

            if (currentPage < totalPages - 1) {
                RegularButton(
                    onClick = onNext,
                    modifier = Modifier.weight(1f),
                    text = "Next"
                )
            } else {
                RegularButton(
                    onClick = onFinish,
                    modifier = Modifier.weight(1f),
                    text = "Finish"
                )
            }
        }
    }
}
