package com.masliaiev.core.models

data class Album(
    val id: String,
    val title: String,
    val smallCoverUrl: String?,
    val mediumCoverUrl: String?,
    val bigCoverUrl: String?
)
