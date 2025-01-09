package nl.hearteye.elearning.ui.components.popup

import android.app.Activity
import android.net.Uri
import android.util.Log
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
    onImageSelected: (String) -> Unit
) {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            uri?.let {
                val base64String = convertUriToBase64(it, context.contentResolver)
                Log.d("ProfilePicturePopUp", "Selected Image Base64: $base64String")
                onImageSelected(base64String)
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
                        launcher.launch("image/*")
                    }) {
                        Text("Choose Image")
                    }
                }
            },
            confirmButton = {
                Button(onClick = onDismiss) {
                    Text("Cancel")
                }
            },
            dismissButton = null
        )
    }
}
