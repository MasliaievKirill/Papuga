package com.masliaiev.papuga.mappers

import com.masliaiev.api.models.AlbumDto
import com.masliaiev.api.models.ArtistDto
import com.masliaiev.api.models.PlaylistDto
import com.masliaiev.api.models.TrackDto
import com.masliaiev.api.models.TracksDto
import com.masliaiev.core.models.Album
import com.masliaiev.core.models.Artist
import com.masliaiev.core.models.Playlist
import com.masliaiev.core.models.Track
import com.masliaiev.core.models.Tracks

fun ArtistDto.toEntity(): Artist {
    return Artist(
        id = this.id,
        name = this.name,
        shareUrl = this.shareUrl,
        mediumPictureUrl = this.mediumPictureUrl,
        bigPictureUrl = this.bigPictureUrl
    )
}

fun AlbumDto.toEntity(): Album {
    return Album(
        id = this.id,
        title = this.title,
        mediumCoverUrl = this.mediumCoverUrl,
        bigCoverUrl = this.bigCoverUrl
    )
}

fun TrackDto.toEntity(): Track {
    return Track(
        id = this.id,
        title = this.title,
        titleShort = this.titleShort,
        shareUrl = this.shareUrl,
        duration = this.duration,
        explicitLyrics = this.explicitLyrics,
        artist = this.artist?.toEntity(),
        album = this.album?.toEntity()
    )
}

fun TracksDto.toEntity(): Tracks {
    return Tracks(
        data = this.data.map {
            it.toEntity()
        }
    )
}

fun PlaylistDto.toEntity(): Playlist {
    return Playlist(
        id = this.id,
        title = this.title,
        description = this.description,
        tracksCount = this.tracksCount,
        fansCount = this.fansCount,
        shareUrl = this.shareUrl,
        mediumPictureUrl = this.mediumPictureUrl,
        bigPictureUrl = this.bigPictureUrl,
        tracks = this.tracks.toEntity()
    )
}