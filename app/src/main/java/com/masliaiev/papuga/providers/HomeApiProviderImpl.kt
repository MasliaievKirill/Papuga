package com.masliaiev.papuga.providers

import com.masliaiev.api.ApiService
import com.masliaiev.core.data.makeRequest
import com.masliaiev.core.models.Playlist
import com.masliaiev.feature.home.data.provider.HomeApiProvider
import com.masliaiev.papuga.mappers.toEntity
import com.masliaiev.utils.playListsIds
import javax.inject.Inject

class HomeApiProviderImpl @Inject constructor(
    private val apiService: ApiService
) : HomeApiProvider {

    override suspend fun getPlayLists(playlistId: String): Result<Playlist?> {
        return makeRequest(
            request = {
                apiService.getChartTracks(playlistId)
            },
            onSuccess = {
                it?.toEntity()
            }
        )
    }

    override fun getCountriesWithId(): List<String> {
        return playListsIds.values.toList()
    }

}