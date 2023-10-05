package com.masliaiev.core.models

data class Playlist(
    val id: String,
    val title: String,
    val description: String,
    val tracksCount: String,
    val fansCount: String?,
    val shareUrl: String?,
    val mediumPictureUrl: String?,
    val bigPictureUrl: String?,
    val tracks: Tracks
)
