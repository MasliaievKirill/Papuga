package com.masliaiev.feature.main.domain.usecases

import androidx.paging.PagingData
import com.masliaiev.core.models.Playlist
import com.masliaiev.feature.main.domain.repository.MainRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class StartUseCase @Inject constructor(
    private val repository: MainRepository
) {
    suspend fun start(): Flow<PagingData<Playlist>> {
        return repository.start()
    }
}