package nl.hearteye.elearning.ui.components.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
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
fun OnboardingStep2(onNextClick: () -> Unit, onBackClick: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.onboarding2_background),
            contentDescription = "Onboarding Background",
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.2f)
                .align(Alignment.TopCenter)
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Image(
                painter = painterResource(id = R.drawable.hearteye_logo_black),
                contentDescription = "HeartEye Logo",
                modifier = Modifier
                    .padding(top = 16.dp)
                    .height(25.dp)
            )
            Spacer(modifier = Modifier.height(42.dp))
            Column(
                modifier = Modifier
                    .width(260.dp)
                    .align(Alignment.Start)
            ) {
                Text(
                    text = "Discussions & Learning",
                    style = typography.titleMedium,
                    modifier = Modifier.align(Alignment.Start)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Join the discussion forum. Share insights, ask questions, and learn from fellow cardiologists.",
                    style = typography.bodyMedium,
                    modifier = Modifier.align(Alignment.Start)
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Image(
                painter = painterResource(id = R.drawable.onboarding2_image),
                contentDescription = "Onboarding image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(245.dp)
            )

            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedButton(onClick = onBackClick, text = "Back")
                RegularButton(onClick = onNextClick, text = "Next")
            }
            Row(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Indicator(isSelected = false)
                Indicator(isSelected = true)
                Indicator(isSelected = false)
            }
        }
    }
}

