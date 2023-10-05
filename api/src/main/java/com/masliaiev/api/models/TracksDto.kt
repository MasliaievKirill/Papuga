package com.masliaiev.api.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class TracksDto(
    @SerializedName("data")
    @Expose
    val data: List<TrackDto>
)
