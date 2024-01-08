package com.masliaiev.player.mappers

import android.os.Bundle
import androidx.core.net.toUri
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import com.masliaiev.core.extensions.getStringOrEmpty
import com.masliaiev.core.models.Album
import com.masliaiev.core.models.Artist
import com.masliaiev.core.models.Track

fun Track.toMediaItem(): MediaItem {
    return MediaItem.Builder()
        .setUri(this.preview)
        .setMediaMetadata(
            MediaMetadata.Builder()
                .setArtist(this.artist?.name)
                .setTitle(this.title)
                .setAlbumTitle(this.album?.title)
                .setArtworkUri(this.album?.bigCoverUrl?.toUri())
                .setExtras(
                    Bundle().apply {
                        putString(KEY_TRACK_ID, this@toMediaItem.id)
                        putString(KEY_TITLE_SHORT, this@toMediaItem.titleShort)
                        putString(KEY_PREVIEW, this@toMediaItem.preview)
                        putString(KEY_TRACK_SHARE_URL, this@toMediaItem.shareUrl)
                        putString(KEY_DURATION, this@toMediaItem.duration)
                        putBoolean(KEY_EXPLICIT_LYRICS, this@toMediaItem.explicitLyrics)
                        putString(KEY_ARTIST_ID, this@toMediaItem.artist?.id)
                        putString(KEY_ARTIST_SHARE_URL, this@toMediaItem.artist?.shareUrl)
                        putString(KEY_ARTIST_MEDIUM_PICTURE_URL, this@toMediaItem.artist?.mediumPictureUrl)
                        putString(KEY_ARTIST_BIG_PICTURE_URL, this@toMediaItem.artist?.bigPictureUrl)
                        putString(KEY_ALBUM_ID, this@toMediaItem.album?.id)
                        putString(KEY_ALBUM_SMALL_COVER_URL, this@toMediaItem.album?.smallCoverUrl)
                        putString(KEY_ALBUM_MEDIUM_COVER_URL, this@toMediaItem.album?.mediumCoverUrl)
                        putString(KEY_ALBUM_BIG_COVER_URL, this@toMediaItem.album?.bigCoverUrl)
                    }
                )
                .build()
        )
        .build()
}

fun MediaMetadata.toTrack(): Track {
    return Track(
        id = this.extras.getStringOrEmpty(KEY_TRACK_ID),
        title = this.title.toString(),
        titleShort = this.extras.getStringOrEmpty(KEY_TITLE_SHORT),
        preview = this.extras.getStringOrEmpty(KEY_PREVIEW),
        shareUrl = this.extras.getStringOrEmpty(KEY_TRACK_SHARE_URL),
        duration = this.extras.getStringOrEmpty(KEY_DURATION),
        explicitLyrics = this.extras?.getBoolean(KEY_EXPLICIT_LYRICS) ?: false,
        artist = Artist(
            id = this.extras.getStringOrEmpty(KEY_ARTIST_ID),
            name = this.artist.toString(),
            shareUrl = this.extras.getStringOrEmpty(KEY_ARTIST_SHARE_URL),
            mediumPictureUrl = this.extras.getStringOrEmpty(KEY_ARTIST_MEDIUM_PICTURE_URL),
            bigPictureUrl = this.extras.getStringOrEmpty(KEY_ARTIST_BIG_PICTURE_URL)
        ),
        album = Album(
            id = this.extras.getStringOrEmpty(KEY_ALBUM_ID),
            title = this.albumTitle.toString(),
            smallCoverUrl = this.extras.getStringOrEmpty(KEY_ALBUM_SMALL_COVER_URL),
            mediumCoverUrl = this.extras.getStringOrEmpty(KEY_ALBUM_MEDIUM_COVER_URL),
            bigCoverUrl = this.extras.getStringOrEmpty(KEY_ALBUM_BIG_COVER_URL)
        )
    )
}

private const val KEY_TRACK_ID = "track_id"
private const val KEY_TITLE_SHORT = "title_short"
private const val KEY_PREVIEW = "preview"
private const val KEY_TRACK_SHARE_URL = "track_share_url"
private const val KEY_DURATION = "duration"
private const val KEY_EXPLICIT_LYRICS = "explicit_lyrics"
private const val KEY_ARTIST_ID = "artist_id"
private const val KEY_ARTIST_SHARE_URL = "artist_share_url"
private const val KEY_ARTIST_MEDIUM_PICTURE_URL = "artist_medium_picture_url"
private const val KEY_ARTIST_BIG_PICTURE_URL = "artist_big_picture_url"
private const val KEY_ALBUM_ID = "album_id"
private const val KEY_ALBUM_SMALL_COVER_URL = "album_small_cover_url"
private const val KEY_ALBUM_MEDIUM_COVER_URL = "album_medium_cover_url"
private const val KEY_ALBUM_BIG_COVER_URL = "album_big_cover_url"