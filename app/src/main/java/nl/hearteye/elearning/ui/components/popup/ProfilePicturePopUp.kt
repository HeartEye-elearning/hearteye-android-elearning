package nl.hearteye.elearning.ui.components.popup

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import nl.hearteye.elearning.ui.utils.convertUriToBase64

@Composable
fun ProfilePicturePopUp(
    isOpen: Boolean,
    onDismiss: () -> Unit,
    onImageSelected: (String) -> Unit,
    onConfirm: (String?) -> Unit
) {
    val context = LocalContext.current
    var selectedImageBase64: String? = null

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            uri?.let {
                selectedImageBase64 = convertUriToBase64(it, context.contentResolver)
            }
        }
    )

    if (isOpen) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text("Upload Profile Picture") },
            text = {
                Column {
                    Text("Select a PNG image to upload.")
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = {
                        launcher.launch("image/png")
                    }) {
                        Text("Choose Image")
                    }
                }
            },
            confirmButton = {
                Button(onClick = {
                    onConfirm(selectedImageBase64)
                }) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                Button(onClick = onDismiss) {
                    Text("Cancel")
                }
            }
        )
    }
}

