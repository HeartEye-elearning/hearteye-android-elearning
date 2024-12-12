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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import nl.hearteye.elearning.R
import nl.hearteye.elearning.ui.components.buttons.RegularButton
import nl.hearteye.elearning.ui.theme.typography

@Composable
fun OnboardingStep1(onNextClick: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.onboarding1_background),
            contentDescription = "Onboarding Background",
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.4f)
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
                    text = "Why this E-Learning course",
                    style = typography.titleMedium,
                    color = Color.White,
                    modifier = Modifier.align(Alignment.Start)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Learn the key differences between a standard ECG and the HeartEye MiniECG. This knowledge is essential for accurate interpretation and patient care.",
                    style = typography.bodyMedium,
                    color = Color.White,
                    modifier = Modifier.align(Alignment.Start)
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Image(
                painter = painterResource(id = R.drawable.onboarding1_image),
                contentDescription = "Onboarding image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(245.dp)
            )

            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                RegularButton(onClick = onNextClick, text = "Next")
            }
            Row(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Indicator(isSelected = true)
                Indicator(isSelected = false)
                Indicator(isSelected = false)
            }
        }
    }
}
