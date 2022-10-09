package com.peterchege.paginatedapp.pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.peterchege.paginatedapp.models.Product
import com.peterchege.paginatedapp.repository.ProductsRepository
import kotlinx.coroutines.delay

class ProductsDataSource (
    private val repository: ProductsRepository,

    ): PagingSource<Int, Product>() {


    override fun getRefreshKey(state: PagingState<Int, Product>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Product> {
        delay(5000L)
        return try {
            val nextPageNumber = params.key ?: 0
            val response = repository.getProducts(limit = 10, page = nextPageNumber)
            LoadResult.Page(
                data = response.products,
                prevKey = null,
                nextKey = if (response.products.isNotEmpty()) nextPageNumber + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}