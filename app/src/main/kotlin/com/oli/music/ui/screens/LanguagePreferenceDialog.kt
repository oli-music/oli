package com.oli.music.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

/**
 * A content language a user can pick on first launch. [tag] maps to the
 * innertube `hl` (host language) param, [region] to `gl` (geolocation),
 * which together drive what Home's charts/quick picks/recommendations show.
 */
data class ContentLanguageOption(
    val displayName: String,
    val nativeName: String,
    val tag: String,
    val region: String,
)

val ContentLanguageOptions = listOf(
    ContentLanguageOption("English", "English", "en", "US"),
    ContentLanguageOption("Spanish", "Español", "es", "ES"),
    ContentLanguageOption("Hindi", "हिन्दी", "hi", "IN"),
    ContentLanguageOption("Tamil", "தமிழ்", "ta", "IN"),
    ContentLanguageOption("Telugu", "తెలుగు", "te", "IN"),
    ContentLanguageOption("Portuguese", "Português", "pt", "BR"),
    ContentLanguageOption("French", "Français", "fr", "FR"),
    ContentLanguageOption("German", "Deutsch", "de", "DE"),
    ContentLanguageOption("Japanese", "日本語", "ja", "JP"),
    ContentLanguageOption("Korean", "한국어", "ko", "KR"),
    ContentLanguageOption("Arabic", "العربية", "ar", "SA"),
    ContentLanguageOption("Indonesian", "Bahasa Indonesia", "id", "ID"),
)

@Composable
fun LanguagePreferenceDialog(
    onLanguageChosen: (tag: String, region: String) -> Unit,
) {
    var selected by remember { mutableStateOf<ContentLanguageOption?>(null) }

    Dialog(
        onDismissRequest = { /* no-op: this is a required first-run choice, not casually dismissible */ },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Card(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(28.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 24.dp, vertical = 28.dp)
            ) {
                Text(
                    text = "Choose your language",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.SemiBold,
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = "Oli will tailor Home's charts, quick picks, and recommendations to this language. You can change this later in Settings.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Spacer(Modifier.height(20.dp))

                LazyColumn(
                    modifier = Modifier.heightIn(max = 340.dp)
                ) {
                    items(ContentLanguageOptions) { option ->
                        val isSelected = selected == option
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(14.dp))
                                .background(
                                    if (isSelected) MaterialTheme.colorScheme.primaryContainer
                                    else MaterialTheme.colorScheme.surface
                                )
                                .clickable { selected = option }
                                .padding(horizontal = 14.dp, vertical = 14.dp)
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = option.nativeName,
                                    style = MaterialTheme.typography.bodyLarge,
                                    fontWeight = FontWeight.Medium,
                                )
                                if (option.nativeName != option.displayName) {
                                    Text(
                                        text = option.displayName,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    )
                                }
                            }
                            if (isSelected) {
                                Icon(
                                    imageVector = Icons.Filled.Check,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary,
                                )
                            }
                        }
                        Spacer(Modifier.height(4.dp))
                    }
                }

                Spacer(Modifier.height(20.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    TextButton(onClick = { onLanguageChosen(com.oli.music.constants.SYSTEM_DEFAULT, com.oli.music.constants.SYSTEM_DEFAULT) }) {
                        Text("Use device language")
                    }
                    Button(
                        onClick = { selected?.let { onLanguageChosen(it.tag, it.region) } },
                        enabled = selected != null,
                    ) {
                        Text("Continue")
                    }
                }
            }
        }
    }
}
