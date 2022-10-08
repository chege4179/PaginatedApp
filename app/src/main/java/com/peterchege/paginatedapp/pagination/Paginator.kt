package com.peterchege.paginatedapp.pagination

interface Paginator<Key, Item> {
    suspend fun loadNextItems()
    fun reset()
}