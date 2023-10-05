package com.masliaiev.api.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class AlbumDto(
    @SerializedName("id")
    @Expose
    val id: String,
    @SerializedName("title")
    @Expose
    val title: String,
    @SerializedName("cover_medium")
    @Expose
    val mediumCoverUrl: String?,
    @SerializedName("cover_big")
    @Expose
    val bigCoverUrl: String?
)
