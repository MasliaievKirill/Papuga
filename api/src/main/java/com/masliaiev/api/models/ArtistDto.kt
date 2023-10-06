package com.masliaiev.api.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ArtistDto(
    @SerializedName("id")
    @Expose
    val id: String,
    @SerializedName("name")
    @Expose
    val name: String,
    @SerializedName("share")
    @Expose
    val shareUrl: String?,
    @SerializedName("picture_medium")
    @Expose
    val mediumPictureUrl: String?,
    @SerializedName("picture_big")
    @Expose
    val bigPictureUrl: String?
)
