package com.masliaiev.api.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PlaylistDto(
    @SerializedName("id")
    @Expose
    val id: String,
    @SerializedName("title")
    @Expose
    val title: String,
    @SerializedName("description")
    @Expose
    val description: String,
    @SerializedName("nb_tracks")
    @Expose
    val tracksCount: String,
    @SerializedName("fans")
    @Expose
    val fansCount: String?,
    @SerializedName("share")
    @Expose
    val shareUrl: String?,
    @SerializedName("picture_medium")
    @Expose
    val mediumPictureUrl: String?,
    @SerializedName("picture_big")
    @Expose
    val bigPictureUrl: String?,
    @SerializedName("tracks")
    @Expose
    val tracks: TracksDto
)
