package com.masliaiev.api

import com.masliaiev.api.models.PlaylistDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("/playlist/{playlist_id}")
    suspend fun getChartTracks(
        @Path(PATH_PLAYLIST_ID) playlistId: String,
    ): Response<PlaylistDto>

    companion object {
        private const val PATH_PLAYLIST_ID = "playlist_id"
    }
}