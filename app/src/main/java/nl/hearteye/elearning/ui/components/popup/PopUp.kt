package nl.hearteye.elearning.ui.components.popup

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import nl.hearteye.elearning.ui.components.buttons.OutlinedButton
import nl.hearteye.elearning.ui.components.buttons.RegularButton
import nl.hearteye.elearning.ui.theme.typography

@Composable
fun Popup(
    isVisible: Boolean,
    title: String,
    text: String,
    confirmButtonText: String = "Confirm",
    cancelButtonText: String = "Cancel",
    onConfirm: () -> Unit,
    onCancel: () -> Unit,
    onDismiss: () -> Unit
) {
    if (isVisible) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f))
                .clickable { onDismiss() }
        ) {
            Card(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(16.dp)
                    .fillMaxWidth()
                    .background(Color.White),
                shape = RoundedCornerShape(24.dp),
            ) {
                Column(
                    modifier = Modifier
                        .background(Color.White)
                        .padding(vertical = 24.dp, horizontal = 16.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = title,
                        style = typography.titleLarge,
                        modifier = Modifier
                            .padding(bottom = 8.dp)
                            .align(Alignment.CenterHorizontally)
                    )

                    Text(
                        text = text,
                        style = typography.bodyLarge,
                        modifier = Modifier
                            .padding(bottom = 16.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        OutlinedButton(
                            text = cancelButtonText,
                            onClick = {
                                onCancel()
                                onDismiss()
                            },
                            modifier = Modifier.weight(1f)
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        RegularButton(
                            text = confirmButtonText,
                            onClick = {
                                onConfirm()
                                onDismiss()
                            },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }
    }
}
