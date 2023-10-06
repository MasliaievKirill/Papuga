package com.masliaiev.feature.home.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.masliaiev.core.models.Playlist
import com.masliaiev.feature.home.data.pagesource.PlaylistsPageSource
import com.masliaiev.feature.home.data.provider.HomeApiProvider
import com.masliaiev.feature.home.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val provider: HomeApiProvider
) : HomeRepository {

    override suspend fun getPlaylists(): Flow<PagingData<Playlist>> {
        return Pager(
            config = PagingConfig(
                pageSize = MAX_NUMBER_OF_ITEMS_LOADED_AT_ONCE,
                enablePlaceholders = true,
                maxSize = MAX_NUMBER_OF_ITEMS
            ),
            pagingSourceFactory = {
                PlaylistsPageSource(
                    provider
                )
            }
        ).flow
    }

    companion object {
        private const val MAX_NUMBER_OF_ITEMS_LOADED_AT_ONCE = 10
        private const val MAX_NUMBER_OF_ITEMS = 100
    }
}