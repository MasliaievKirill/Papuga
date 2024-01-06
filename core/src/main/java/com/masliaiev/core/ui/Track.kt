package com.masliaiev.core.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.masliaiev.core.R
import com.masliaiev.core.constants.EmptyConstants
import com.masliaiev.core.models.Album
import com.masliaiev.core.models.Artist
import com.masliaiev.core.models.Track
import com.masliaiev.core.theme.ExtraSmall
import com.masliaiev.core.theme.Medium
import com.masliaiev.core.theme.Small

@Composable
fun Track(
    track: Track,
    index: Int? = null,
    onClick: (() -> Unit)? = null,
    onMoreClick: (() -> Unit)? = null
) {
    Card(
        modifier = Modifier
            .padding(vertical = Small),
        shape = RoundedCornerShape(Medium)
    ) {
        Box(
            modifier = Modifier.clickable {
                onClick?.invoke()
            }
        ){
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = Small),
                verticalAlignment = Alignment.CenterVertically
            ) {
                index?.let {
                    Text(
                        modifier = Modifier.padding(start = Medium),
                        text = (index + 1).toString(),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = Medium)
                ) {
                    Text(
                        text = track.title,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        fontWeight = FontWeight.Bold
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (track.explicitLyrics == true) {
                            Icon(
                                modifier = Modifier
                                    .padding(end = ExtraSmall)
                                    .size(Medium),
                                painter = painterResource(id = R.drawable.ic_explicit),
                                contentDescription = EmptyConstants.EMPTY_STRING
                            )
                        }
                        Text(
                            text = track.artist?.name ?: EmptyConstants.EMPTY_STRING,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1
                        )
                    }
                }
                onMoreClick?.let {
                    IconButton(
                        onClick = it
                    ) {
                        Icon(
                            imageVector = Icons.Filled.MoreVert,
                            contentDescription = EmptyConstants.EMPTY_STRING
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TrackPreview() {
    Track(
        track = Track(
            id = "131",
            title = "Track title",
            titleShort = "Short t",
            preview = "",
            shareUrl = "",
            duration = "345",
            explicitLyrics = false,
            artist = Artist(
                id = "111",
                name = "Artist Name",
                shareUrl = "",
                mediumPictureUrl = null,
                bigPictureUrl = null
            ),
            album = Album(
                id = "323",
                title = "Album title",
                smallCoverUrl = null,
                mediumCoverUrl = null,
                bigCoverUrl = null
            )
        ),
        index = 1,
        onMoreClick = {}
    )
}