package nl.hearteye.elearning.ui.screens.more

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.outlined.Help
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Language
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import nl.hearteye.elearning.R
import nl.hearteye.elearning.ui.components.more.MoreScreenItem
import nl.hearteye.elearning.ui.theme.ForegroundPrimary
import nl.hearteye.elearning.ui.theme.typography

@Composable
fun MoreScreen(
    moreViewModel: MoreViewModel = hiltViewModel()
) {
    val selectedLanguage = remember { mutableStateOf("English") }
    val currentUser = moreViewModel.currentUser.value
    val errorMessage = moreViewModel.errorMessage.value

    LaunchedEffect(Unit) {
        moreViewModel.fetchCurrentUser()
        val savedLanguage = moreViewModel.getSelectedLanguage() ?: "eng"
        selectedLanguage.value = moreViewModel.mapLanguageCodeToDisplayName(savedLanguage)
    }

    if (currentUser == null) {
        if (errorMessage != null) {
            Text(text = "Error: $errorMessage", color = Color.Red)
        } else {
            CircularProgressIndicator(color = ForegroundPrimary,)
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.profile_picture),
                    contentDescription = "Profile Image",
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .border(2.dp, Color.Gray, CircleShape)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "${currentUser.firstName} ${currentUser.lastName}",
                    style = typography.bodyLarge,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    modifier = Modifier
                        .background(ForegroundPrimary, RoundedCornerShape(50))
                        .padding(horizontal = 12.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Completed Main Course",
                        color = Color.White,
                        style = typography.bodyMedium,
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        imageVector = Icons.Default.EmojiEvents,
                        contentDescription = "Medal Icon",
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .shadow(
                        elevation = 4.dp,
                        shape = RoundedCornerShape(
                            topStart = 8.dp,
                            topEnd = 8.dp,
                            bottomStart = 0.dp,
                            bottomEnd = 0.dp
                        ),
                        clip = false
                    )
                    .clip(
                        RoundedCornerShape(
                            topStart = 8.dp,
                            topEnd = 8.dp,
                            bottomStart = 0.dp,
                            bottomEnd = 0.dp
                        )
                    )
                    .background(Color.White)
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Column {
                    Text(
                        text = "Account",
                        style = typography.titleMedium,
                        color = Color.Black,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                    MoreScreenItem(
                        icon = Icons.Outlined.Person,
                        label = "Edit Profile",
                        showArrow = true
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(vertical = 8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Language,
                            contentDescription = "Language Icon",
                            tint = Color.Black,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = selectedLanguage.value,
                            style = typography.bodyMedium,
                            color = Color.Black,
                            modifier = Modifier
                                .weight(1f)
                                .clickable {
                                    val newLanguage =
                                        if (selectedLanguage.value == "English") "nl" else "eng"
                                    selectedLanguage.value =
                                        moreViewModel.mapLanguageCodeToDisplayName(newLanguage)
                                    moreViewModel.saveLanguagePreference(newLanguage)
                                }
                        )
                    }
                    MoreScreenItem(
                        icon = Icons.Outlined.Notifications,
                        label = "Notifications",
                        switch = true,
                        showArrow = false
                    )
                    MoreScreenItem(icon = Icons.Outlined.Lock, label = "Privacy", showArrow = true)
                    MoreScreenItem(
                        icon = Icons.Outlined.Help,
                        label = "Logout",
                        showArrow = false,
                        onClick = { moreViewModel.logout() }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Column {
                    Text(
                        text = "Support & About",
                        style = typography.titleMedium,
                        color = Color.Black,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                    MoreScreenItem(
                        icon = Icons.Outlined.Help,
                        label = "Help & Support",
                        showArrow = true
                    )
                    MoreScreenItem(
                        icon = Icons.Outlined.Info,
                        label = "Knowledge Base",
                        showArrow = true
                    )
                }
            }
        }
    }
}
