package com.masliaiev.feature.home.data.provider

import com.masliaiev.core.models.Playlist
import com.masliaiev.core.models.response.Error
import com.masliaiev.core.models.response.NetworkResponse

interface HomeApiProvider {

    suspend fun getPlayLists(playlistId: String): NetworkResponse<Playlist, Error>

    fun getCountriesWithId(): List<String>
}