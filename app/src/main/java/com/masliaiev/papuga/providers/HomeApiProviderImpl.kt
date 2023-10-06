package com.masliaiev.papuga.providers

import com.masliaiev.api.ApiService
import com.masliaiev.core.models.Playlist
import com.masliaiev.core.models.response.Error
import com.masliaiev.core.models.response.NetworkResponse
import com.masliaiev.feature.home.data.provider.HomeApiProvider
import com.masliaiev.papuga.extensions.getErrorOrNull
import com.masliaiev.papuga.mappers.toEntity
import com.masliaiev.utils.playListsIds
import javax.inject.Inject

class HomeApiProviderImpl @Inject constructor(
    private val apiService: ApiService
) : HomeApiProvider {

    override suspend fun getPlayLists(playlistId: String): NetworkResponse<Playlist, Error> {
        return apiService.getChartTracks(playlistId).run {
            NetworkResponse(
                data = body()?.toEntity(),
                error = getErrorOrNull()
            )
        }
    }

    override fun getCountriesWithId(): List<String> {
        return playListsIds.values.toList()
    }

}