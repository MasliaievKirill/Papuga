package com.masliaiev.papuga.providers

import com.masliaiev.api.ApiService
import com.masliaiev.core.models.Playlist
import com.masliaiev.core.models.response.Error
import com.masliaiev.core.models.response.NetworkResponse
import com.masliaiev.feature.playlist.data.provider.PlaylistApiProvider
import com.masliaiev.papuga.extensions.getErrorOrNull
import com.masliaiev.papuga.mappers.toEntity
import javax.inject.Inject

class PlaylistApiProviderImpl @Inject constructor(
    private val apiService: ApiService
) : PlaylistApiProvider {


    override suspend fun getPlayList(playlistId: String): NetworkResponse<Playlist, Error> {
        return apiService.getChartTracks(playlistId).run {
            NetworkResponse(
                data = body()?.toEntity(),
                error = getErrorOrNull()
            )
        }
    }
}