package com.masliaiev.papuga.providers

import com.masliaiev.api.ApiService
import com.masliaiev.core.data.makeRequest
import com.masliaiev.core.models.Playlist
import com.masliaiev.feature.playlist.data.provider.PlaylistApiProvider
import com.masliaiev.papuga.mappers.toEntity
import javax.inject.Inject

class PlaylistApiProviderImpl @Inject constructor(
    private val apiService: ApiService
) : PlaylistApiProvider {


    override suspend fun getPlayList(playlistId: String): Result<Playlist?> {
        return makeRequest(
            request = {
                apiService.getChartTracks(playlistId)
            },
            onSuccess = {
                it?.toEntity()
            }
        )
    }
}