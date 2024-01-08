package com.masliaiev.feature.home.data.pagesource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.masliaiev.core.models.Playlist
import com.masliaiev.feature.home.data.provider.HomeApiProvider

class PlaylistsPageSource(
    private val provider: HomeApiProvider
) : PagingSource<Int, Playlist>() {

    override fun getRefreshKey(state: PagingState<Int, Playlist>): Int {
        return FIRST_PAGE
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Playlist> {

        val position = params.key ?: 1

        return when (position) {
            1 -> loadPlayLists(position, 0, 9)
            2 -> loadPlayLists(position, 10, 19)
            3 -> loadPlayLists(position, 20, 29)
            4 -> loadPlayLists(position, 30, 39)
            5 -> loadPlayLists(position, 40, 49)
            6 -> loadPlayLists(position, 50, 59)
            7 -> loadPlayLists(position, 60, 66)
            else -> LoadResult.Error(
                Throwable(
                    message = "Page does not exist"
                )
            )
        }
    }

    private suspend fun loadPlayLists(
        position: Int,
        startIndex: Int,
        endIndex: Int
    ): LoadResult<Int, Playlist> {
        val countriesWithIs = provider.getCountriesWithId()
        val playlists = mutableListOf<Playlist>()
        var throwable: Throwable? = null
        countriesWithIs.forEachIndexed { index, id ->
            if (index in startIndex..endIndex) {
                provider.getPlayLists(id)
                    .onSuccess { playlist ->
                        playlist?.let {
                            playlists.add(it)
                        }
                    }
                    .onFailure {
                        throwable = it
                    }
            }
        }
        return throwable?.let {
            LoadResult.Error(it)
        } ?: run {
            LoadResult.Page(
                data = playlists,
                prevKey = if (position == 1) null else position - 1,
                nextKey = if (playlists.isEmpty()) null else position + 1
            )
        }
    }

    companion object {
        private const val FIRST_PAGE = 1
    }
}