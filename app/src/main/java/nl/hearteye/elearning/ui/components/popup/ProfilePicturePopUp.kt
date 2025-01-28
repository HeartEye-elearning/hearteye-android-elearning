package nl.hearteye.elearning.ui.components.popup

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import nl.hearteye.elearning.ui.components.buttons.OutlinedButton
import nl.hearteye.elearning.ui.components.buttons.RegularButton
import nl.hearteye.elearning.ui.utils.cropAndConvertToBase64

@Composable
fun ProfilePicturePopUp(
    isOpen: Boolean,
    onDismiss: () -> Unit,
    onImageSelected: (String) -> Unit,
    onConfirm: (String?) -> Unit
) {
    val context = LocalContext.current
    val selectedImageBase64 = remember { mutableStateOf<String?>(null) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            uri?.let {
                val base64 = cropAndConvertToBase64(it, context)
                selectedImageBase64.value = base64
            }
        }
    )

    if (isOpen) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text("Upload Profile Picture") },
            text = {
                Column {
                    Text("Select a PNG or JPG image to upload.")
                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedButton(onClick = { launcher.launch("image/png") }, text = "Choose Image")
                    Spacer(modifier = Modifier.height(16.dp))
                    selectedImageBase64.value?.let {
                        Text("Image selected (Base64 length: ${it.length})")
                    }
                }
            },
            confirmButton = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    OutlinedButton(
                        onClick = onDismiss,
                        text = "Cancel",
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    RegularButton(
                        onClick = { onConfirm(selectedImageBase64.value) },
                        text = "Confirm",
                        modifier = Modifier.weight(1f)
                    )
                }
            },
            dismissButton = {}
        )
    }
}

