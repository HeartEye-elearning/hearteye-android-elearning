package nl.hearteye.elearning.ui.components.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import nl.hearteye.elearning.R
import nl.hearteye.elearning.ui.components.buttons.OutlinedButton
import nl.hearteye.elearning.ui.components.buttons.RegularButton
import nl.hearteye.elearning.ui.theme.ForegroundPrimary
import nl.hearteye.elearning.ui.theme.typography

@Composable
fun OnboardingStep3(onContinueClick: (String) -> Unit, onBackClick: () -> Unit) {
    val isLanguageSelected = remember { mutableStateOf(false) }
    val selectedLanguage = remember { mutableStateOf("") }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.onboarding3_background),
            contentDescription = "Onboarding Background",
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.45f)
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
                    text = "Language Selection",
                    style = typography.titleMedium,
                    color = Color.White,
                    modifier = Modifier.align(Alignment.Start)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Choose your language: Dutch or English.",
                    style = typography.bodyMedium,
                    color = Color.White,
                    modifier = Modifier.align(Alignment.Start)
                )
            }
            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .background(
                            color = if (selectedLanguage.value == "eng") ForegroundPrimary else Color.White,
                            shape = RoundedCornerShape(10.dp)
                        )
                        .clickable {
                            selectedLanguage.value = "eng"
                            isLanguageSelected.value = true
                        }
                        .padding(top = 8.dp, start = 16.dp, end = 16.dp)
                        .width(100.dp)
                        .height(70.dp)
                ) {
                    Text(
                        text = "ENG",
                        style = typography.bodyMedium,
                        color = if (selectedLanguage.value == "eng") Color.White else Color.Black
                    )
                    Image(
                        painter = painterResource(id = R.drawable.eng_flag),
                        contentDescription = "English Flag",
                        modifier = Modifier.height(50.dp)
                    )
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .background(
                            color = if (selectedLanguage.value == "nl") ForegroundPrimary else Color.White,
                            shape = RoundedCornerShape(10.dp)
                        )
                        .clickable {
                            selectedLanguage.value = "nl"
                            isLanguageSelected.value = true
                        }
                        .padding(top = 8.dp, start = 16.dp, end = 16.dp)
                        .width(100.dp)
                        .height(70.dp)
                ) {
                    Text(
                        text = "NL",
                        style = typography.bodyMedium,
                        color = if (selectedLanguage.value == "nl") Color.White else Color.Black
                    )
                    Image(
                        painter = painterResource(id = R.drawable.nl_flag),
                        contentDescription = "Dutch Flag",
                        modifier = Modifier.height(50.dp)
                    )
                }
            }




            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedButton(onClick = onBackClick, text = "Back")
                RegularButton(
                    onClick = { onContinueClick(selectedLanguage.value) },
                    text = "Continue",
                    enabled = isLanguageSelected.value,
                )
            }

            Row(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Indicator(isSelected = false)
                Indicator(isSelected = false)
                Indicator(isSelected = true)
            }
        }
    }
}
