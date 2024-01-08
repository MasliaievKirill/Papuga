package com.masliaiev.feature.home.data.provider

import com.masliaiev.core.models.Playlist

interface HomeApiProvider {

    suspend fun getPlayLists(playlistId: String): Result<Playlist?>

    fun getCountriesWithId(): List<String>
}