package com.masliaiev.core.models

data class Track(
    val id: String,
    val title: String,
    val titleShort: String,
    val preview: String,
    val shareUrl: String?,
    val duration: String?,
    val explicitLyrics: Boolean,
    val artist: Artist?,
    val album: Album?
)
