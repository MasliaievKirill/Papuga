package com.masliaiev.feature.main.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.masliaiev.core.models.Playlist
import com.masliaiev.feature.main.data.pagesource.PlaylistsPageSource
import com.masliaiev.feature.main.data.provider.MainApiProvider
import com.masliaiev.feature.main.domain.repository.MainRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val provider: MainApiProvider
) : MainRepository {

    override suspend fun start(): Flow<PagingData<Playlist>> {
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

    companion object{
        private const val MAX_NUMBER_OF_ITEMS_LOADED_AT_ONCE = 10
        private const val MAX_NUMBER_OF_ITEMS = 100
    }
}