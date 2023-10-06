package com.masliaiev.core.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import com.masliaiev.core.theme.Small

@Composable
fun InformationCard(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = MaterialTheme.colorScheme.primary,
    imageVector: ImageVector? = null
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(Small),
        colors = CardDefaults.cardColors(
            containerColor = color
        )
    ) {
        Row(
            modifier = Modifier.padding(Small),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Small)
        ) {
            imageVector?.let {
                Icon(
                    imageVector = it,
                    contentDescription = null
                )
            }
            Text(
                text = text,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        }
    }
}