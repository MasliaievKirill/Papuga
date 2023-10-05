package com.masliaiev.api.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class TrackDto(
    @SerializedName("id")
    @Expose
    val id: String,
    @SerializedName("title")
    @Expose
    val title: String,
    @SerializedName("title_short")
    @Expose
    val titleShort: String,
    @SerializedName("share")
    @Expose
    val shareUrl: String?,
    @SerializedName("duration")
    @Expose
    val duration: String?,
    @SerializedName("explicit_lyrics")
    @Expose
    val explicitLyrics: Boolean?,
    @SerializedName("artist")
    @Expose
    val artist: ArtistDto?,
    @SerializedName("album")
    @Expose
    val album: AlbumDto?
)
